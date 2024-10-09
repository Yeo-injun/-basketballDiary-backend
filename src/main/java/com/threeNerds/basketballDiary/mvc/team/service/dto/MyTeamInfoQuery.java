package com.threeNerds.basketballDiary.mvc.team.service.dto;

import com.threeNerds.basketballDiary.mvc.team.mapper.dto.TeamInfoDTO;
import com.threeNerds.basketballDiary.mvc.team.mapper.dto.TeamRegularExerciseDTO;
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

        private final TeamInfoDTO teamInfo;
        private final Integer memberCount;
        private final List<TeamRegularExerciseDTO> regularExercises;
        Result( TeamInfoDTO teamInfo, Integer memberCount, List<TeamRegularExerciseDTO> regularExercises ) {
            this.teamInfo           = teamInfo;
            this.memberCount        = memberCount;
            this.regularExercises   = regularExercises;
        }
    }
    public MyTeamInfoQuery.Result buildResult( TeamInfoDTO teamInfo, Integer memberCount, List<TeamRegularExerciseDTO> regularExercises ) {
        return new Result( teamInfo, memberCount, regularExercises );
    }
}
