package com.threeNerds.basketballDiary.pagination;

import lombok.Getter;

@Getter
public class PagerDTO {
    private static final Integer DEFAULT_ROW_COUNT = 10; // TODO 확인하기 static아니어도 가능한지
    private Integer pageNo; // 현재 page 번호
    private Integer offset; // 시작 row 수

    private Integer rowCount = DEFAULT_ROW_COUNT; // 한 page에 보여줄 row 수
    private Integer totalPageCount; // 총 page 수
    private Integer totalCount; // 총 row 수

    public PagerDTO (Integer pageNo) {
        if (pageNo < 1) {
            pageNo = 1;
        }
        this.pageNo = pageNo;
        this.offset = this.rowCount * (pageNo - 1);
    }

    public PagerDTO (Integer pageNo, Integer rowCount) {
        if (pageNo < 1) {
            pageNo = 1;
        }
        this.pageNo = pageNo;
        this.rowCount = rowCount;
        this.offset = rowCount * (pageNo - 1);
    }

    public PagerDTO pageNo (Integer pageNo) {
        this.pageNo = pageNo;
        return this;
    }

    public PagerDTO offset (Integer offset) {
        this.offset = offset;
        return this;
    }

    public PagerDTO setPagingData (Integer totalCount) {
        this.totalCount = totalCount;
        this.totalPageCount = (int) Math.ceil((double) totalCount / this.rowCount);
        return this;
    }
}
