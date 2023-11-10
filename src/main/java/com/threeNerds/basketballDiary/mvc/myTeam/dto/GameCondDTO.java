package com.threeNerds.basketballDiary.mvc.myTeam.dto;

import com.threeNerds.basketballDiary.pagination.PagerDTO;
import lombok.Getter;

@Getter
public class GameCondDTO {
    private Long userSeq;
    private Long teamSeq;
    private PagerDTO pagination;
    private String gameBgngYmd;
    private String gameEndYmd;
    private String sidoCode;
    private String gamePlaceName;
    private String gameTypeCode;
    private String homeAwayCode;

    public GameCondDTO userSeq(Long userSeq) {
        this.userSeq = userSeq;
        return this;
    }

    public GameCondDTO teamSeq(Long teamSeq) {
        this.teamSeq = teamSeq;
        return this;
    }

    public GameCondDTO pagination( Integer pageNo ) {
        this.pagination = new PagerDTO( pageNo, 5 );
        return this;
    }

    public GameCondDTO gameBgngYmd(String gameBgngYmd) {
        this.gameBgngYmd = gameBgngYmd;
        return this;
    }

    public GameCondDTO gameEndYmd(String gameEndYmd) {
        this.gameEndYmd = gameEndYmd;
        return this;
    }

    public GameCondDTO sidoCode(String sidoCode) {
        this.sidoCode = sidoCode;
        return this;
    }
    public GameCondDTO gamePlaceName(String gamePlaceName) {
        this.gamePlaceName = gamePlaceName;
        return this;
    }
    public GameCondDTO gameTypeCode(String gameTypeCode) {
        this.gameTypeCode = gameTypeCode;
        return this;
    }
    public GameCondDTO homeAwayCode(String homeAwayCode) {
        this.homeAwayCode = homeAwayCode;
        return this;
    }
}

