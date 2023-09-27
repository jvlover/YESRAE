package com.ssafy.yesrae.domain.user.service;

import com.ssafy.yesrae.domain.user.dto.request.UserFollowCheckGetReq;
import com.ssafy.yesrae.domain.user.dto.request.UserFollowPostReq;
import com.ssafy.yesrae.domain.user.dto.request.UserLoginPostReq;
import com.ssafy.yesrae.domain.user.dto.request.UserRegistPostReq;
import com.ssafy.yesrae.domain.user.dto.response.UserCheckEmailRes;
import com.ssafy.yesrae.domain.user.dto.response.UserCheckNicknameRes;
import com.ssafy.yesrae.domain.user.dto.response.UserFindRes;
import com.ssafy.yesrae.domain.user.dto.response.UserFollowFindRes;
import com.ssafy.yesrae.domain.user.dto.response.UserNicknameFindRes;
import java.util.List;

public interface UserService {

    public void regist(UserRegistPostReq userRegistPostReq);

    public UserFindRes oauthLogin(String accessToken);

    public void follow(UserFollowPostReq userFollowPostReq);

    public List<UserFollowFindRes> findFollow(Long userId);

    public boolean checkFollow(UserFollowCheckGetReq userFollowCheckGetReq);

    public UserNicknameFindRes findNickname(Long userId);

    public UserFindRes login(UserLoginPostReq userLoginPostReq);

    public UserCheckEmailRes checkEmail(String email);

    public UserCheckNicknameRes checkNickname(String nickname);
}
