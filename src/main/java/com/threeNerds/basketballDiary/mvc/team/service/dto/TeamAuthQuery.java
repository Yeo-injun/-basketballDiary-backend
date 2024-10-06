package com.threeNerds.basketballDiary.mvc.team.service.dto;

import com.threeNerds.basketballDiary.auth.constant.AuthLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamAuthQuery {

    Long userSeq;
}
