package com.threeNerds.basketballDiary.mvc.game.dto;

import com.threeNerds.basketballDiary.pagination.Pagination;
import lombok.Getter;

@Getter
public class SearchPlayersDTO {

    private Long gameSeq;
    private String homeAwayCode;
    private Pagination pagination;

    public SearchPlayersDTO gameSeq(Long gameSeq) {
        this.gameSeq = gameSeq;
        return this;
    }

    public SearchPlayersDTO homeAwayCode(String homeAwayCode) {
        this.homeAwayCode = homeAwayCode;
        return this;
    }

    public SearchPlayersDTO pagination( Pagination pagination ) {
        this.pagination = pagination;
        return this;
    }
}
