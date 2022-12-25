package com.threeNerds.basketballDiary.mvc.myTeam.dto;

import com.threeNerds.basketballDiary.pagination.PagerDTO;
import lombok.Getter;

@Getter
public class SearchMyTeamDTO {

    private Long userSeq;
    private Integer pageNo;
    private PagerDTO pagerDTO;

    public SearchMyTeamDTO userSeq(Long userSeq) {
        this.userSeq = userSeq;
        return this;
    }

    public SearchMyTeamDTO pageNo(Integer pageNo) {
        this.pageNo = pageNo;
        return this;
    }

    public SearchMyTeamDTO pagerDTO(PagerDTO pagerDTO) {
        this.pagerDTO = pagerDTO;
        return this;
    }
}
