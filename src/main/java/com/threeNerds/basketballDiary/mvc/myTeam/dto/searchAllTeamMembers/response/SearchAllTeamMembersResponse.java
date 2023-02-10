package com.threeNerds.basketballDiary.mvc.myTeam.dto.searchAllTeamMembers.response;

import com.threeNerds.basketballDiary.mvc.myTeam.dto.MemberDTO;
import com.threeNerds.basketballDiary.pagination.PagerDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class SearchAllTeamMembersResponse {

    private PagerDTO pager;
    private List<MemberDTO> teamMembers;

    public SearchAllTeamMembersResponse(PagerDTO pager, List<MemberDTO> teamMembers) {
        this.pager = pager;
        this.teamMembers = teamMembers;
    }
}
