package com.green.greengramver6.feed.comment;

import com.green.greengramver6.feed.comment.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedCommentService {
    private final FeedCommentMapper mapper;

    public long feedPostComment(FeedCommentPostReq p) {
        mapper.insFeedComment(p);
        return p.getFeedCommentId();
    }

    public FeedCommentGetRes feedGetComment(FeedCommentGetReq p) {
        FeedCommentGetRes res = new FeedCommentGetRes();
        if (p.getStartIdx() < 0) {
            res.setCommentList(new ArrayList<>());
            return res;
        }
        List<FeedCommentDto> commentList = mapper.selFeedCommentList(p);
        res.setCommentList(commentList);
        res.setMoreComment(commentList.size() == p.getSize());

        if (res.isMoreComment()) {
            commentList.remove(commentList.size() - 1);
        }
        return res;
    }

    public int delFeedComment(FeedCommentDelReq p) {
        int result = mapper.delFeedComment(p);
        return result;
    }
}
