package com.green.greengramver6.feed.comment.model;

import lombok.Getter;

import java.beans.ConstructorProperties;

@Getter
public class FeedCommentDelReq {
    private long feedCommentId;
    private long signedUserId;

    @ConstructorProperties({"signed_user_id", "feed_comment_id"})
    public FeedCommentDelReq(long signedUserId, long feedCommentId) {
        this.signedUserId = signedUserId;
        this.feedCommentId = feedCommentId;
    }
}
