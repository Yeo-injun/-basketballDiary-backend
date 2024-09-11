package com.threeNerds.basketballDiary.mvc.myTeam.service.dto;

import com.threeNerds.basketballDiary.mvc.myTeam.dto.MyTeamDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.TeamInfoDTO;
import com.threeNerds.basketballDiary.mvc.team.dto.TeamRegularExerciseDTO;
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
public class MyTeamInfoQuery {
    /* 사용자Seq */
    private Long userSeq;
    /* 팀Seq */

    private Long teamSeq;

    @Getter
    public class Result {

        TeamInfoDTO teamInfo;
        List<TeamRegularExerciseDTO> regularExercises;
        Result( TeamInfoDTO teamInfo, List<TeamRegularExerciseDTO> regularExercises ) {
            this.teamInfo           = teamInfo;
            this.regularExercises   = regularExercises;
        }
    }
    public MyTeamInfoQuery.Result buildResult( TeamInfoDTO teamInfo, List<TeamRegularExerciseDTO> regularExercises ) {
        return new Result( teamInfo, regularExercises );
    }
}
