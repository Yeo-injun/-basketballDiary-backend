package com.threeNerds.basketballDiary.mvc.dto;

import lombok.Getter;

@Getter
public class PagerDTO {
    private Integer pageNo = 0;
    private Integer offset = 10;

    public PagerDTO pageNo (Integer pageNo) {
        this.pageNo = pageNo;
        return this;
    }

    public PagerDTO offset (Integer offset) {
        this.offset = offset;
        return this;
    }
}
