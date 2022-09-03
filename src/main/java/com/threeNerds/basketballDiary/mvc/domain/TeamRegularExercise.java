package com.threeNerds.basketballDiary.mvc.domain;

import com.threeNerds.basketballDiary.mvc.dto.team.team.TeamRegularExerciseDTO;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamRegularExercise {
    /* PK */
    private Long teamRegularExerciseSeq;
    /* FK: 팀Seq */
    private Long teamSeq;
    /* 요일코드 */
    private String dayOfTheWeekCode;
    /* 정기운동시작시간 */
    private String startTime;
    /* 정기운동종료시간 */
    private String endTime;
    /* 정기운동장소주소 */
    private String exercisePlaceAddress;
    /* 정기운동장소명 */
    private String exercisePlaceName;

    public static TeamRegularExercise create(Long teamSeq, TeamRegularExerciseDTO regularExercise)
    {
        return TeamRegularExercise.builder()
                .teamSeq(teamSeq)
                .startTime(regularExercise.getStartTime())
                .endTime(regularExercise.getEndTime())
                .dayOfTheWeekCode(regularExercise.getDayOfTheWeekCode())
                .exercisePlaceAddress(regularExercise.getExercisePlaceAddress())
                .exercisePlaceName(regularExercise.getExercisePlaceName())
                .build();
    }
}
