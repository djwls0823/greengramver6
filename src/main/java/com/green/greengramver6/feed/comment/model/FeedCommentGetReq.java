package com.green.greengramver6.feed.comment.model;

import com.green.greengramver6.common.Constants;
import lombok.Getter;

import java.beans.ConstructorProperties;

@Getter
public class FeedCommentGetReq {
    private final static int FIRST_COMMENT_SIZE = 3;

    private long feedId;
    private int startIdx;
    private int size;

    @ConstructorProperties({"feed_id", "start_idx", "size"})
    public FeedCommentGetReq(long feedId, int startIdx, Integer size) {
        this.feedId = feedId;
        this.startIdx = startIdx;
        this.size = (size == null ? Constants.getDefault_page_size() : size) + 1;
    }
}
