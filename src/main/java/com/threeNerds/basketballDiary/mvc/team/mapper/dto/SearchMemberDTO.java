package com.threeNerds.basketballDiary.mvc.team.mapper.dto;

import com.threeNerds.basketballDiary.pagination.Pagination;
import lombok.Getter;

/**
 * Author: 강창기
 * 설명: 소속팀 Controller에서 팀원 및 운영진의 정보를 관리하는 DTO
 */

@Getter
public class SearchMemberDTO {

    /* 페이징 처리를 위한 VO */
    private Pagination pagination;
    /* 팀Seq */
    private Long teamSeq;
    /* 이름 */
    private String userName;

    public SearchMemberDTO( Pagination pagination, Long teamSeq ) {
        this.pagination = pagination;
        this.teamSeq = teamSeq;
    }

    public SearchMemberDTO( Pagination pagination, Long teamSeq, String userName ) {
        this.pagination = pagination;
        this.teamSeq    = teamSeq;
        this.userName   = userName;
    }

}
