package com.threeNerds.basketballDiary.mvc.team.mapper.dto;

import com.threeNerds.basketballDiary.pagination.Pagination;
import lombok.Getter;

@Getter
public class SearchMyTeamDTO {

    private Long userSeq;
    private Pagination pagination;

    public SearchMyTeamDTO userSeq(Long userSeq) {
        this.userSeq = userSeq;
        return this;
    }

    public SearchMyTeamDTO pagination( Pagination pagination ) {
        this.pagination = pagination;
        return this;
    }
}
