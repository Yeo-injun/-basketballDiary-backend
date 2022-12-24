package com.threeNerds.basketballDiary.mvc.dto.pagination;

import com.threeNerds.basketballDiary.mvc.myTeam.dto.MemberDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class PaginatedTeamMemeberDTO {

    private PagerDTO pager;
    private List<MemberDTO> teamMembers;

    public PaginatedTeamMemeberDTO(PagerDTO pager, List<MemberDTO> teamMembers) {
        this.pager = pager;
        this.teamMembers = teamMembers;
    }
}
