package com.threeNerds.basketballDiary.mvc.team.controller.response;

import com.threeNerds.basketballDiary.mvc.team.mapper.dto.TeamJoinRequestDTO;
import com.threeNerds.basketballDiary.mvc.team.service.dto.InvitationQuery;
import lombok.Getter;

import java.util.List;

@Getter
public class GetInvitationsResponse {

    private final List<TeamJoinRequestDTO> invitations;

    public GetInvitationsResponse( InvitationQuery.Result result ) {
        this.invitations = result.getInvitations();
    }

}
