package com.threeNerds.basketballDiary.mvc.myTeam.dto.getTeamMembers.response;

import com.threeNerds.basketballDiary.mvc.myTeam.dto.MemberDTO;
import com.threeNerds.basketballDiary.pagination.PagerDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class GetTeamMembersResponse {

    private PagerDTO pagination;
    private List<MemberDTO> teamMembers;

    public GetTeamMembersResponse(PagerDTO pagination, List<MemberDTO> teamMembers) {
        this.pagination = pagination;
        this.teamMembers = teamMembers;
    }
}
