package com.green.greengramver6.feed.comment;

import com.green.greengramver6.common.model.ResultResponse;
import com.green.greengramver6.feed.comment.model.FeedCommentDelReq;
import com.green.greengramver6.feed.comment.model.FeedCommentGetReq;
import com.green.greengramver6.feed.comment.model.FeedCommentGetRes;
import com.green.greengramver6.feed.comment.model.FeedCommentPostReq;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("feed/comment")
public class FeedCommentController {
    private final FeedCommentService service;

    @PostMapping
    public ResultResponse<Long> postComment(@RequestBody FeedCommentPostReq p) {
        long result = service.feedPostComment(p);
        return ResultResponse.<Long>builder()
                .resultMessage("댓글")
                .resultData(result)
                .build();
    }

    @GetMapping
    public ResultResponse<FeedCommentGetRes> feedGetComment(@ParameterObject @ModelAttribute FeedCommentGetReq p) {
        FeedCommentGetRes res = service.feedGetComment(p);
        return ResultResponse.<FeedCommentGetRes>builder()
                             .resultMessage(String.format("%d rows", res.getCommentList().size()))
                             .resultData(res)
                             .build();
    }

    @DeleteMapping
    public ResultResponse<Integer> delFeedComment(@ParameterObject @ModelAttribute FeedCommentDelReq p) {
        int result = service.delFeedComment(p);
        return ResultResponse.<Integer>builder()
                             .resultMessage("삭제 완료")
                             .resultData(result)
                             .build();
    }
}
