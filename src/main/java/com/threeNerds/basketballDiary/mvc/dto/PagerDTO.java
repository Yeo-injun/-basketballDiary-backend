package com.threeNerds.basketballDiary.mvc.dto;

import lombok.Getter;

@Getter
public class PagerDTO {
    private Integer pageNo = 0;
    private Integer offset = 4;
    private Integer totalCount;

    public PagerDTO pageNo (Integer pageNo) {
        this.pageNo = pageNo;
        return this;
    }

    public PagerDTO offset (Integer offset) {
        this.offset = offset;
        return this;
    }

    public PagerDTO totalCount (Integer totalCount) {
        this.totalCount = totalCount;
        return this;
    }
}
