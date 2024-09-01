package com.threeNerds.basketballDiary.mvc.myTeam.controller.response;

import com.threeNerds.basketballDiary.mvc.myTeam.dto.TeamJoinRequestDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.service.dto.JoinRequestQuery;
import lombok.Getter;

import java.util.List;

@Getter
public class GetReceivedJoinRequestsResponse {
    List<TeamJoinRequestDTO> joinRequests;
    public GetReceivedJoinRequestsResponse( JoinRequestQuery.Result result ) {
        this.joinRequests = result.getJoinRequests();
    }
}
