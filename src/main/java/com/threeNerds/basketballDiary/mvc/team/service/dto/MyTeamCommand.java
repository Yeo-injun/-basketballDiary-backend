package com.threeNerds.basketballDiary.mvc.team.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyTeamCommand {

    /* 팀Seq */
    private Long teamSeq;
    /* 팀장사용자Seq */
    private Long leaderUserSeq;
}
