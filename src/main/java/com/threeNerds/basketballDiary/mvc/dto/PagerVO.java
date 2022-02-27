package com.threeNerds.basketballDiary.mvc.dto;

import lombok.Getter;

@Getter
public class PagerVO {
    private Integer pageNo = 0;
    private Integer offset = 10;

    public PagerVO pageNo (Integer pageNo) {
        this.pageNo = pageNo;
        return this;
    }

    public PagerVO offset (Integer offset) {
        this.offset = offset;
        return this;
    }
}
