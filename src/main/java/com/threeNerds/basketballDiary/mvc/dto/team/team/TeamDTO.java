package com.threeNerds.basketballDiary.mvc.dto.team.team;

import com.threeNerds.basketballDiary.mvc.domain.TeamRegularExercise;
import lombok.Getter;

import java.util.List;

@Getter
public class TeamDTO {

    /* 팀 정보 */
    private TeamInfoDTO teamInfoDTO;

    /* 정기운동 목록 */
    private List<TeamRegularExercise> teamRegularExercisesList;

    public TeamDTO teamInfoDTO (TeamInfoDTO teamInfoDTO) {
        this.teamInfoDTO = teamInfoDTO;
        return this;
    }

    public TeamDTO teamRegularExercisesList (List<TeamRegularExercise> teamRegularExercisesList) {
        this.teamRegularExercisesList = teamRegularExercisesList;
        return this;
    }
}
