package com.threeNerds.basketballDiary.mvc.myTeam.controller.response;

import com.threeNerds.basketballDiary.mvc.myTeam.dto.MyTeamDTO;
import com.threeNerds.basketballDiary.pagination.PagerDTO;
import com.threeNerds.basketballDiary.pagination.Pagination;
import lombok.Getter;

import java.util.List;

@Getter
public class GetMyTeamsResponse {
    private Pagination pagination;
    private List<MyTeamDTO> myTeams;

    public GetMyTeamsResponse( Pagination pagination, List<MyTeamDTO> myTeams ) {
        this.pagination = pagination;
        this.myTeams    = myTeams;
    }
}
