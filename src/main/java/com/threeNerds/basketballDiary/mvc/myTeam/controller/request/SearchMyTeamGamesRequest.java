package com.threeNerds.basketballDiary.mvc.myTeam.controller.request;

import lombok.Getter;

@Getter
public class SearchMyTeamGamesRequest {

    /* 사용자Seq */
    private Long userSeq;
    /* 팀Seq */
    private Long teamSeq;
    /* 페이지 번호 */
    private Integer pageNo;

    private String gameBgngYmd;
    private String gameEndYmd;
    private String sidoCode;
    private String gamePlaceName;
    private String gameTypeCode;
    private String homeAwayCode;

    /**
     * 기본생성자 선언 - MessageBinding시 Jackson 라이브러리 정책 때문에 선언
     */
    public SearchMyTeamGamesRequest() {

    }

    public SearchMyTeamGamesRequest(
        Long userSeq,           Long teamSeq,           Integer pageNo,
        String gameBgngYmd,     String gameEndYmd,      String sidoCode,
        String gamePlaceName,   String gameTypeCode,    String homeAwayCode
    ) {
        this.userSeq    = userSeq;
        this.teamSeq    = teamSeq;
        this.pageNo     = pageNo;
        this.gameBgngYmd 		= gameBgngYmd;
        this.gameEndYmd 		= gameEndYmd;
        this.sidoCode 		    = sidoCode;
        this.gamePlaceName 		= gamePlaceName;
        this.gameTypeCode 		= gameTypeCode;
        this.homeAwayCode 		= homeAwayCode;
    }
}
