package com.green.greengramver6.feed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FeedPostReq {
    @JsonIgnore
    private long feedId;

    private long writerUserId;
    private String contents;
    private String location;
}
