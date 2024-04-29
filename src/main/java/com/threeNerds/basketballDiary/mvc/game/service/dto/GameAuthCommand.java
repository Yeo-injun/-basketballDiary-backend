package com.threeNerds.basketballDiary.mvc.game.service.dto;

import com.threeNerds.basketballDiary.auth.Auth;
import com.threeNerds.basketballDiary.auth.constant.AuthLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameAuthCommand {
    Long gameSeq;
    Long userSeq;
    Long gameJoinPlayerSeq;
}
