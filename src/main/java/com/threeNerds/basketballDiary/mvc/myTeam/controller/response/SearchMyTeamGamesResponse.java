package com.threeNerds.basketballDiary.mvc.myTeam.controller.response;

import com.threeNerds.basketballDiary.mvc.myTeam.dto.GameRecordDTO;
import com.threeNerds.basketballDiary.pagination.Pagination;
import lombok.Getter;

import java.util.List;

@Getter
public class SearchMyTeamGamesResponse {

    /* 페이징 */
    private Pagination pagination;
    /* 게임목록 */
    private List<GameRecordDTO> games;

    /**
     * 기본생성자 선언 - MessageBinding시 Jackson 라이브러리 정책 때문에 선언
     */
    public SearchMyTeamGamesResponse() {

    }

    public SearchMyTeamGamesResponse( Pagination pagination, List<GameRecordDTO> games ) {
        // TODO 페이지 rowCount 세팅 방법 고민 ( Request의 페이징 객체 생성과 맞춰야 하는데 변경시 한곳을 바쑤지 않아서 오류가 발생할 수 있음.., )
        this.pagination = pagination;
        this.games      = games;
    }
}
