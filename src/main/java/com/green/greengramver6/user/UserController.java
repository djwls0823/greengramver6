package com.green.greengramver6.user;

import com.green.greengramver6.common.model.ResultResponse;
import com.green.greengramver6.user.model.*;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {
    private final UserService service;

    @PostMapping("sign-up")
    public ResultResponse<Integer> postSignUp(@RequestPart UserSignUpReq p, @RequestPart(required = false) MultipartFile pic) {
        int result = service.postSignUp(p, pic);
        return ResultResponse.<Integer>builder()
                             .resultMessage("회원가입 완료")
                             .resultData(result)
                             .build();
    }

    @PostMapping("sign-in")
    public ResultResponse<UserSignInRes> postSignIn(@RequestBody UserSignInReq p) {
        UserSignInRes res = service.postSignIn(p);
        return ResultResponse.<UserSignInRes>builder()
                             .resultMessage(res.getMessage())
                             .resultData(res)
                             .build();
    }

    @GetMapping
    @Operation(summary = "유저 프로필 정보")
    public ResultResponse<UserInfoGetRes> getUserInfo(@ParameterObject @ModelAttribute UserInfoGetReq p) {
        log.info("UserController > getUserInfo > p: {}", p);
        UserInfoGetRes res = service.getUserInfo(p);
        return ResultResponse.<UserInfoGetRes>builder()
                             .resultMessage("유저 프로필 정보")
                             .resultData(res)
                             .build();
    }

    @PatchMapping("pic")
    public ResultResponse<String> patchProfilePic(@ModelAttribute UserPicPatchReq p) {
        log.info("UserController > patchProfilePic > p: {}", p);
        String pic = service.patchUserPic(p);
        return ResultResponse.<String>builder()
                             .resultMessage("프로필 사진 수정 완료")
                             .resultData(pic)
                             .build();
    }
}
