package com.green.greengramver6.feed.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class FeedPostRes {
    private long feedId;
    private List<String> pics;
}
