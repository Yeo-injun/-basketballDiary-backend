package com.threeNerds.basketballDiary.mvc.team.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamAuthCommand {
    Long teamSeq;
    Long teamMemberSeq;
}
