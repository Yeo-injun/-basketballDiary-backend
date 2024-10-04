package com.threeNerds.basketballDiary.mvc.team.controller.response;

import com.threeNerds.basketballDiary.mvc.team.dto.TeamDTO;
import com.threeNerds.basketballDiary.mvc.team.service.dto.TeamQuery;
import com.threeNerds.basketballDiary.pagination.Pagination;
import lombok.Getter;

import java.util.List;

@Getter
public class SearchTeamsResponse {

    private Pagination pagination;
    private List<TeamDTO> teams;

    public SearchTeamsResponse( TeamQuery.Result result ) {
        this.pagination = result.getPagination();
        this.teams      = result.getTeams();
    }

}
