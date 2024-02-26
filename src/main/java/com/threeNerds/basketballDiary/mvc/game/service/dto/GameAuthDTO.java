package com.threeNerds.basketballDiary.mvc.game.service.dto;

import com.threeNerds.basketballDiary.auth.constant.AuthLevel;
import lombok.Getter;

import java.util.Map;
import java.util.Set;

@Getter
public class GameAuthDTO {
    Long userSeq;
    Map< Long, AuthLevel > authGames;

    public GameAuthDTO(Long userSeq, Map< Long, AuthLevel > authGames ) {
        this.userSeq        = userSeq;
        this.authGames      = authGames;
    }
}
