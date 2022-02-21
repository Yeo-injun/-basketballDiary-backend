package com.threeNerds.basketballDiary.mvc.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
}
