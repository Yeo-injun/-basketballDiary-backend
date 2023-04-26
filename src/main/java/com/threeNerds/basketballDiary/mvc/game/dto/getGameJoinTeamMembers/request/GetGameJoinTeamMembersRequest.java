package com.threeNerds.basketballDiary.mvc.game.dto.getGameJoinTeamMembers.request;

import com.threeNerds.basketballDiary.http.RequestJsonBody;
import lombok.Getter;

@Getter
public class GetGameJoinTeamMembersRequest extends RequestJsonBody {

    private Long gameSeq;
    private String homeAwayCode;

    public GetGameJoinTeamMembersRequest( Long gameSeq, String homeAwayCode ) {
        this.gameSeq = gameSeq;
        this.homeAwayCode = homeAwayCode;
    }

}
