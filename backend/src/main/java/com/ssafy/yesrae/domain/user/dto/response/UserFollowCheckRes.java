package com.ssafy.yesrae.domain.user.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserFollowCheckRes {

    Boolean isFollowed;
}
