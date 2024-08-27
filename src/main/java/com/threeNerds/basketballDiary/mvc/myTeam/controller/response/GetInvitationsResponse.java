package com.threeNerds.basketballDiary.mvc.myTeam.controller.response;

import com.threeNerds.basketballDiary.mvc.myTeam.dto.InvitationDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.TeamInfoDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.service.dto.InvitationQuery;
import com.threeNerds.basketballDiary.mvc.team.dto.TeamRegularExerciseDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class GetInvitationsResponse {

    private final List<InvitationDTO> invitations;

    public GetInvitationsResponse( InvitationQuery.Result result ) {
        this.invitations = result.getInvitations();
    }

}
