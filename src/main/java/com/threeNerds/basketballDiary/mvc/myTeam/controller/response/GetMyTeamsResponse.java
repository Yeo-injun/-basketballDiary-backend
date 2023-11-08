package com.threeNerds.basketballDiary.mvc.myTeam.controller.response;

import com.threeNerds.basketballDiary.mvc.myTeam.dto.MyTeamDTO;
import com.threeNerds.basketballDiary.pagination.PagerDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class GetMyTeamsResponse {
    private PagerDTO pagination;
    private List<MyTeamDTO> myTeams;

    public GetMyTeamsResponse( PagerDTO pagination, List<MyTeamDTO> myTeams ) {
        this.pagination = pagination;
        this.myTeams    = myTeams;
    }
}
