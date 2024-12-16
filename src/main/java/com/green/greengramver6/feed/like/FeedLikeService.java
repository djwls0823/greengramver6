package com.green.greengramver6.feed.like;

import com.green.greengramver6.feed.like.model.FeedLikeReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedLikeService {
    private final FeedLikeMapper mapper;

    public int feedLikeToggle(FeedLikeReq p) {
        int result = mapper.delFeedLike(p);
        if (result == 0) {
            return mapper.insFeedLike(p);
        }
        return 0;
    }
}
