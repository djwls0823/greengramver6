package com.green.greengramver6.feed.comment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
//Data Transfer Object
public class FeedCommentDto {
    @JsonIgnore
    private long feedId;
    private long feedCommentId;
    private String comment;
    private long writerUserId;
    private String writerNm;
    private String writerPic;
}
