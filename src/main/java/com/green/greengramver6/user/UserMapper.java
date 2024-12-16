package com.green.greengramver6.user;

import com.green.greengramver6.user.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    int insSignUp(UserSignUpReq p);
    UserSignInRes selSignIn(String uid);
    UserInfoGetRes selUserInfo(UserInfoGetReq p);
    UserInfoGetRes selUserInfo2(UserInfoGetReq p);
    int updUserPic(UserPicPatchReq p);
}
