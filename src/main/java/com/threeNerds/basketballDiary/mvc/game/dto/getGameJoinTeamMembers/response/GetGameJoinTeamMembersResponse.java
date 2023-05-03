package com.threeNerds.basketballDiary.mvc.game.dto.getGameJoinTeamMembers.response;

import com.threeNerds.basketballDiary.http.RequestJsonBody;
import com.threeNerds.basketballDiary.http.ResponseJsonBody;
import com.threeNerds.basketballDiary.mvc.game.dto.GameJoinTeamMemberDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class GetGameJoinTeamMembersResponse extends ResponseJsonBody {

    private List<GameJoinTeamMemberDTO> gameJoinTeamMembers;

    public GetGameJoinTeamMembersResponse( List<GameJoinTeamMemberDTO> gameJoinTeamMembers ) {
        this.gameJoinTeamMembers = gameJoinTeamMembers;
    }

}
