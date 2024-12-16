package com.green.greengramver6.feed;

import com.green.greengramver6.common.MyFileUtils;
import com.green.greengramver6.feed.comment.FeedCommentMapper;
import com.green.greengramver6.feed.comment.model.FeedCommentDto;
import com.green.greengramver6.feed.comment.model.FeedCommentGetReq;
import com.green.greengramver6.feed.comment.model.FeedCommentGetRes;
import com.green.greengramver6.feed.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedService {
    private final FeedMapper feedMapper;
    private final FeedPicMapper feedPicMapper;
    private final MyFileUtils myFileUtils;
    private final FeedCommentMapper feedCommentMapper;

    @Transactional
    public FeedPostRes postFeed(List<MultipartFile> pics, FeedPostReq p) {
        int result = feedMapper.insFeed(p);

        String middlePath = String.format("feed/%d", p.getFeedId());
        myFileUtils.makeFolders(middlePath);

        List<String> picNameList = new ArrayList<>(pics.size());
        for (MultipartFile pic : pics) {
            String savedPicName = myFileUtils.makeRandomFileName(pic);
            picNameList.add(savedPicName);
            String filePath = String.format("%s/%s", middlePath, savedPicName);
            try {
                myFileUtils.transferTo(pic, filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FeedPicDto feedPicDto = new FeedPicDto();
        feedPicDto.setPics(picNameList);
        feedPicDto.setFeedId(p.getFeedId());
        int resultPics = feedPicMapper.insFeedPics(feedPicDto);

        return FeedPostRes.builder()
                .feedId(p.getFeedId())
                .pics(picNameList)
                .build();
    }

    public List<FeedGetRes> getFeedList(FeedGetReq p) {
        // N+1 이슈 발생
        List<FeedGetRes> list = feedMapper.selFeedList(p);
        for (FeedGetRes item : list) {
            //피드 당 사진 리스트
            item.setPics(feedPicMapper.selFeedPicList(item.getFeedId()));

            //피드 당 댓글 4개
            FeedCommentGetReq req = new FeedCommentGetReq(item.getFeedId(), 0, 3);
            List<FeedCommentDto> commentList = feedCommentMapper.selFeedCommentList(req);

            FeedCommentGetRes res = new FeedCommentGetRes();
            res.setCommentList(commentList);
            res.setMoreComment(commentList.size() == req.getSize());

            if (res.isMoreComment()) {
                commentList.remove(commentList.size() - 1);
            }
            item.setComment(res);
        }
        return list;
    }

    //select 2번
    public List<FeedGetRes> getFeedList2(FeedGetReq p) {
        return null;
    }

    //select 3번, 피드 5,000개 있음, 페이지당 20개씩 가져온다.
    public List<FeedGetRes> getFeedList3(FeedGetReq p) {
        //피드 리스트
        List<FeedGetRes> list = feedMapper.selFeedList(p);

        //feed_id를 골라내야 한다.
        List<Long> feedIds4 = list.stream().map(FeedGetRes::getFeedId).collect(Collectors.toList());
        List<Long> feedIds5 = list.stream().map(item -> ((FeedGetRes)item).getFeedId()).toList();
        List<Long> feedIds6 = list.stream().map(item -> { return ((FeedGetRes)item).getFeedId();}).toList();

        List<Long> feedIds = new ArrayList<>(list.size());
        for(FeedGetRes item : list) {
            feedIds.add(item.getFeedId());
        }
        log.info("feedIds: {}", feedIds);

        //피드와 관련된 사진 리스트
        List<FeedPicSel> feedPicList = feedPicMapper.selFeedPicListByFeedIds(feedIds);
        log.info("feedPicList: {}", feedPicList);

        Map<Long, List<String>> picHashMap = new HashMap<>();
        for(FeedPicSel item : feedPicList) {
            long feedId = item.getFeedId();
            if(!picHashMap.containsKey(feedId)) {
                picHashMap.put(feedId, new ArrayList<String>(2));
            }
            List<String> pics = picHashMap.get(feedId);
            pics.add(item.getPic());
        }

        //피드와 관련된 댓글 리스트
        List<FeedCommentDto> feedCommentList = feedCommentMapper.selFeedCommentListByFeedIdsLimit4(feedIds);
        Map<Long, FeedCommentGetRes> commentHashMap = new HashMap<>();
        for(FeedCommentDto item : feedCommentList) {
            long feedId = item.getFeedId();
            if(!commentHashMap.containsKey(feedId)) {
                FeedCommentGetRes feedCommentGetRes = new FeedCommentGetRes();
                feedCommentGetRes.setCommentList(new ArrayList<>());
                commentHashMap.put(feedId, feedCommentGetRes);
            }
            FeedCommentGetRes feedCommentGetRes = commentHashMap.get(feedId);
            feedCommentGetRes.getCommentList().add(item);
        }

        for(FeedGetRes res : list) {
            res.setPics(picHashMap.get(res.getFeedId()));
            FeedCommentGetRes feedCommentGetRes = commentHashMap.get(res.getFeedId());

            if(feedCommentGetRes == null) {
                feedCommentGetRes = new FeedCommentGetRes();
                feedCommentGetRes.setCommentList(new ArrayList<>());
            } else if (feedCommentGetRes.getCommentList().size() == 4) {
                feedCommentGetRes.setMoreComment(true);
                feedCommentGetRes.getCommentList().remove(feedCommentGetRes.getCommentList().size() - 1);
            }
            res.setComment(feedCommentGetRes);
        }
        log.info("list: {}", list);
        return list;
    }

    @Transactional
    public int deleteFeed(FeedDeleteReq p) {
        //피드 사진 삭제 (폴더 삭제)
        String deletePath = String.format("%s/feed/%d", myFileUtils.getUploadPath(), p.getFeedId());
        myFileUtils.deleteFolder(deletePath, true);
        //피드 댓글, 좋아요 삭제
        int affectedRows = feedMapper.delFeedLikeAndFeedCommentAndFeedPic(p);
        log.info("affectedRows: {}", affectedRows);
        //피드 삭제
        return feedMapper.delFeed(p);
    }
}
