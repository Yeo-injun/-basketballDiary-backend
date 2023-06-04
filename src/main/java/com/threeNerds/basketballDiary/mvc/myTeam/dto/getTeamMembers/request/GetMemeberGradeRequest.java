package com.threeNerds.basketballDiary.mvc.myTeam.dto.getTeamMembers.request;

import com.threeNerds.basketballDiary.pagination.PagerDTO;
import lombok.Getter;

@Getter
public class GetMemeberGradeRequest {

    private Long teamSeq;
    private PagerDTO pagerDTO;

    public GetMemeberGradeRequest(Long teamSeq, PagerDTO pagerDTO) {
        this.teamSeq = teamSeq;
        this.pagerDTO = pagerDTO;
    }

    public GetMemeberGradeRequest(Long teamSeq, Integer pageNo) {
        this.teamSeq = teamSeq;
        pagerDTO = new PagerDTO(pageNo);
    }
}
