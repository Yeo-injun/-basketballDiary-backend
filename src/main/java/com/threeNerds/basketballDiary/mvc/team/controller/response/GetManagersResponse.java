package com.threeNerds.basketballDiary.mvc.team.controller.response;

import com.threeNerds.basketballDiary.mvc.game.service.dto.TeamMemberQuery;
import com.threeNerds.basketballDiary.mvc.team.mapper.dto.MemberDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class GetManagersResponse {

    List<MemberDTO> managers;

    public GetManagersResponse( TeamMemberQuery.Result result ) {
        this.managers = result.getTeamMembers();
    }
}
