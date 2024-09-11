package com.threeNerds.basketballDiary.mvc.team.dto;

import com.threeNerds.basketballDiary.constant.code.type.DayOfTheWeekCode;
import lombok.Getter;

@Getter
public class TeamRegularExerciseDTO {
    /* PK */
    private Long teamRegularExerciseSeq;
    /* FK: 팀Seq */
    private Long teamSeq;
    /* 요일코드 */
    private String dayOfTheWeekCode;
    /* 요일코드명 */
    private String dayOfTheWeekCodeName;
    /* 정기운동시작시간 */
    private String startTime;
    /* 정기운동종료시간 */
    private String endTime;
    /* 정기운동장소주소 */
    private String exercisePlaceAddress;
    /* 정기운동장소명 */
    private String exercisePlaceName;

    public void setDayOfTheWeekCode( String dayOfTheWeekCode ) {
        this.dayOfTheWeekCode       = dayOfTheWeekCode;
        this.dayOfTheWeekCodeName   = DayOfTheWeekCode.nameOf( dayOfTheWeekCode );
    }
}
