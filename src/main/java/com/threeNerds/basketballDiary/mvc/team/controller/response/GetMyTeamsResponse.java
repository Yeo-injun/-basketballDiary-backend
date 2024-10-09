package com.threeNerds.basketballDiary.mvc.team.controller.response;

import com.threeNerds.basketballDiary.mvc.team.mapper.dto.MyTeamDTO;
import com.threeNerds.basketballDiary.mvc.team.service.dto.MyTeamQuery;
import com.threeNerds.basketballDiary.pagination.Pagination;
import lombok.Getter;

import java.util.List;

@Getter
public class GetMyTeamsResponse {
    private Pagination pagination;
    private List<MyTeamDTO> myTeams;

    public GetMyTeamsResponse( MyTeamQuery.Result result ) {
        this.pagination = result.getPagination();
        this.myTeams    = result.getMyTeams();
    }
}
