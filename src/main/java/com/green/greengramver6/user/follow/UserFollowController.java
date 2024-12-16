package com.green.greengramver6.user.follow;

import com.green.greengramver6.common.model.ResultResponse;
import com.green.greengramver6.user.follow.model.UserFollowReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("user/follow")
public class UserFollowController {
    private final UserFollowService service;

    // 팔로우 신청
    // @RequestBody, 요청을 보내는 자가 body에 json 형태의 데이터를 담아서 보낸다는 뜻
    @PostMapping
    public ResultResponse<Integer> postUserFollow(@RequestBody UserFollowReq p) {
        log.info("UserFollowController > postUserFollow > p :{}", p);
        int result = service.postUserFollow(p);
        return ResultResponse.<Integer>builder()
                .resultMessage("팔로우 성공")
                .resultData(result)
                .build();
    }

    // 팔로우 취소
    // 요청을 보내는 자가 데이터를 어떻게 보내나? Query-String
    @DeleteMapping
    public ResultResponse<Integer> deleteUserFollow(@ParameterObject @ModelAttribute UserFollowReq p) {
        int result = service.deleteUserFollow(p);
        return ResultResponse.<Integer>builder()
                             .resultMessage("팔로우 취소")
                             .resultData(result)
                             .build();
    }
}
