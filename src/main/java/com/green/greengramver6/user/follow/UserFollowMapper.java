package com.green.greengramver6.user.follow;

import com.green.greengramver6.user.follow.model.UserFollowReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserFollowMapper {
    int insUserFollow(UserFollowReq p);
    int delUserFollow(UserFollowReq p);
}
