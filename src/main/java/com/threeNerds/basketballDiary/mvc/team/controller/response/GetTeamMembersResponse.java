package com.threeNerds.basketballDiary.mvc.team.controller.response;

import com.threeNerds.basketballDiary.mvc.game.service.dto.TeamMemberQuery;
import com.threeNerds.basketballDiary.mvc.team.mapper.dto.MemberDTO;
import com.threeNerds.basketballDiary.pagination.Pagination;
import lombok.Getter;

import java.util.List;

@Getter
public class GetTeamMembersResponse {

    private Pagination pagination;
    private List<MemberDTO> teamMembers;

    public GetTeamMembersResponse( TeamMemberQuery.Result result ) {
        this.pagination     = result.getPagination();
        this.teamMembers    = result.getTeamMembers();
    }
}
