package com.ssafy.yesrae.domain.comment.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlayListCommentFindRes {

    private Long PlayListId;

    private Long userId;

    private String content;

    private LocalDateTime creaetedAt;

}
