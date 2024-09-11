package com.threeNerds.basketballDiary.mvc.myTeam.service.dto;

import com.threeNerds.basketballDiary.mvc.myTeam.dto.MyTeamDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.ProfileDTO;
import com.threeNerds.basketballDiary.pagination.Pagination;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyTeamQuery {

    /* 사용자Seq */
    private Long userSeq;
    /* 페이지 번호 */
    private Integer pageNo;

    @Getter
    public class Result {

        List<MyTeamDTO> myTeams;
        Pagination pagination;
        Result( List<MyTeamDTO> myTeams, Pagination pagination ) {
            this.myTeams    = myTeams;
            this.pagination = pagination;
        }
    }
    public MyTeamQuery.Result buildResult( List<MyTeamDTO> myTeams, Pagination pagination ) {
        return new Result( myTeams, pagination );
    }
}
