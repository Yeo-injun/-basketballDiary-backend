package com.threeNerds.basketballDiary.pagination;

import lombok.Getter;

@Getter
public class Pagination {
    private static final Integer DEFAULT_ROW_COUNT = 15; // TODO 화면에서 페이징 처리를 하지 않아 조회되지 않은 데이터가 존재하여 Default값 변경
    private Integer pageNo;         // 현재 page 번호
    private Integer rowCount;       // 한 page에 보여줄 row 수
    private Integer totalCount;     // 총 row 수

    private Integer offset;         // 시작 row 수
    private Integer totalPageCount; // 총 page 수

    private Pagination ( Integer pageNo, Integer rowCount, Integer totalCount ) {
        this.pageNo     = pageNo < 1 ? 1 : pageNo;
        this.rowCount   = rowCount;
        this.totalCount = totalCount;

        this.offset         = rowCount * ( this.pageNo - 1 );
        this.totalPageCount = (int) Math.ceil( (double) totalCount / rowCount );
    }

    public static Pagination of( Integer pageNo ) {
        return new Pagination( pageNo, DEFAULT_ROW_COUNT, 0 );
    }

    public static Pagination of( Integer pageNo, Integer rowCount ) {
        return new Pagination( pageNo, rowCount, 0 );
    }

    public Pagination empty() {
        return new Pagination( 1, this.rowCount, 0 );
    }

    public Pagination getPages( Integer totalCount ) {
        return new Pagination( this.pageNo, this.rowCount, totalCount );
    }

}
