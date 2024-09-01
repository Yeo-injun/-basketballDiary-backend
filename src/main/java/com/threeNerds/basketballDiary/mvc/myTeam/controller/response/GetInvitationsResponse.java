package com.threeNerds.basketballDiary.mvc.myTeam.controller.response;

import com.threeNerds.basketballDiary.mvc.myTeam.dto.TeamJoinRequestDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.service.dto.InvitationQuery;
import lombok.Getter;

import java.util.List;

@Getter
public class GetInvitationsResponse {

    private final List<TeamJoinRequestDTO> invitations;

    public GetInvitationsResponse( InvitationQuery.Result result ) {
        this.invitations = result.getInvitations();
    }

}
