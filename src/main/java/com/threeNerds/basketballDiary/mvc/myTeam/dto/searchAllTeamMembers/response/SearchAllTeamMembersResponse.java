package com.threeNerds.basketballDiary.mvc.myTeam.dto.searchAllTeamMembers.response;

import com.threeNerds.basketballDiary.mvc.myTeam.dto.MemberDTO;
import com.threeNerds.basketballDiary.pagination.PagerDTO;
import com.threeNerds.basketballDiary.pagination.Pagination;
import lombok.Getter;

import java.util.List;

@Getter
public class SearchAllTeamMembersResponse {

    private Pagination pagination;
    private List<MemberDTO> teamMembers;

    public SearchAllTeamMembersResponse( Pagination pagination, List<MemberDTO> teamMembers ) {
        this.pagination = pagination;
        this.teamMembers = teamMembers;
    }
}
