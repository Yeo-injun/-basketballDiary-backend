package com.threeNerds.basketballDiary.mvc.myTeam.dto.getTeamMembers.response;

import com.threeNerds.basketballDiary.mvc.myTeam.dto.MemberDTO;
import com.threeNerds.basketballDiary.pagination.Pagination;
import lombok.Getter;

import java.util.List;

@Getter
public class GetTeamMembersResponse {

    private Pagination pagination;
    private List<MemberDTO> teamMembers;

    public GetTeamMembersResponse( Pagination pagination, List<MemberDTO> teamMembers ) {
        this.pagination = pagination;
        this.teamMembers = teamMembers;
    }
}
