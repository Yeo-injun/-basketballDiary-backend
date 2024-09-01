package com.threeNerds.basketballDiary.mvc.myTeam.service.dto;

import com.threeNerds.basketballDiary.constant.code.type.JoinRequestStateCode;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.TeamJoinRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvitationQuery {
    private Long teamSeq;
    private Long userSeq;
    private JoinRequestStateCode joinRequestState;

    @Getter
    public class Result {
        private final List<TeamJoinRequestDTO> invitations;

        Result( List<TeamJoinRequestDTO> invitations ) {
            this.invitations = invitations;
        }
    }
    public InvitationQuery.Result buildResult( List<TeamJoinRequestDTO> invitations ) {
        return new Result( invitations );
    }
}
