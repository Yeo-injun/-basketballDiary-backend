package com.threeNerds.basketballDiary.mvc.team.controller.response;

import com.threeNerds.basketballDiary.mvc.team.mapper.dto.TeamGameDTO;
import com.threeNerds.basketballDiary.mvc.team.service.dto.TeamGameQuery;
import com.threeNerds.basketballDiary.pagination.Pagination;
import lombok.Getter;

import java.util.List;

@Getter
public class SearchTeamGamesResponse {

    /* 페이징 */
    private Pagination pagination;
    /* 게임목록 */
    private List<TeamGameDTO> games;


    public SearchTeamGamesResponse( TeamGameQuery.Result result ) {
        this.pagination = result.getPagination();
        this.games      = result.getGames();
    }
}
