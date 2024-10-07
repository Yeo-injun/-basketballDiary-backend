package com.threeNerds.basketballDiary.mvc.game.mapper.dto;

import com.threeNerds.basketballDiary.pagination.Pagination;
import lombok.Getter;

@Getter
public class SearchOppenentsDTO {

    String sidoCode;    /*시도코드*/

    String teamName;    /*팀이름*/

    String leaderName;  /*리더이름*/

    Pagination pagination; /* 페이징 처리 객체 */

    public SearchOppenentsDTO sidoCode(String sidoCode) {
        this.sidoCode = sidoCode;
        return this;
    }
    public SearchOppenentsDTO teamName(String teamName) {
        this.teamName = teamName;
        return this;
    }
    public SearchOppenentsDTO leaderName(String leaderName) {
        this.leaderName = leaderName;
        return this;
    }

    public SearchOppenentsDTO pagination(Pagination pagination ) {
        this.pagination = pagination;
        return this;
    }
}
