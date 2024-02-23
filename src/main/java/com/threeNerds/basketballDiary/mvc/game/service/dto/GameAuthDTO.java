package com.threeNerds.basketballDiary.mvc.game.service.dto;

import java.util.Set;

public class GameAuthDTO {
    Long userSeq;
    Set<String> creatorGames;
    Set<String> recorderGames;

    public GameAuthDTO( Long userSeq, Set<String> creatorGames, Set<String> recorderGames ) {
        this.userSeq        = userSeq;
        this.creatorGames   = creatorGames;
        this.recorderGames  = recorderGames;
    }
}
