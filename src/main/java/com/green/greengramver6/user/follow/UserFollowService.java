package com.green.greengramver6.user.follow;

import com.green.greengramver6.user.follow.model.UserFollowReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserFollowService {
    private final UserFollowMapper mapper;

    public int postUserFollow(UserFollowReq p) {
        return mapper.insUserFollow(p);
    }

    public int deleteUserFollow(UserFollowReq p) {
        return mapper.delUserFollow(p);
    }
}
