package com.threeNerds.basketballDiary.mvc.dto;

import com.threeNerds.basketballDiary.mvc.domain.TeamRegularExercise;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter @Setter
public class MyTeamDTO {

    /* 소속팀 정보 */
    MyTeamInfoDTO  myTeamInfoDTO;

    /* 정기운동 목록 */
    List<TeamRegularExercise> teamRegularExercisesList;
}
