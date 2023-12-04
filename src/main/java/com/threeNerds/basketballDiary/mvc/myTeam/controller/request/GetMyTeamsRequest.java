package com.threeNerds.basketballDiary.mvc.myTeam.controller.request;

import com.threeNerds.basketballDiary.mvc.team.dto.TeamRegularExerciseDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class GetMyTeamsRequest {

    /* 사용자Seq */
    private Long userSeq;
    /* 페이지 번호 */
    private Integer pageNo;

    /**
     * 기본생성자 선언 - MessageBinding시 Jackson 라이브러리 정책 때문에 선언
     */
    public GetMyTeamsRequest() {

    }

    public GetMyTeamsRequest( Long userSeq, Integer pageNo ) {
        this.userSeq    = userSeq;
        this.pageNo     = pageNo;
    }
}
