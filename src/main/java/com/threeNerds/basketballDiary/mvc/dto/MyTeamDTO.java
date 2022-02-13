package com.threeNerds.basketballDiary.mvc.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MyTeamDTO {

    /* 팀 pk */
    public Long teamSeq;
    /* 팀 권한 코드 */
    public String teamAuthCode;
    /* 팀명 */
    public String teamName;
    /* 팀 이미지 경로 */
    public String teamImagePath;
    /* 연고지 */
    public String hometown;
    /* 시도코드 */
    public String sidoCode;
    /* 시군구코드 */
    public String sigunguCode;
    /* 경기장명(체육관명) */
    public String exercisePlaceName;
    /* 회원수 */
    public String totMember;
    /* 창단일 */
    public String foundationYmd;
    /* 요일코드 */
    public String dayOfWeekCode;
    /* 정기운동시작시간 */
    public String startTime;
    /* 정기운동종료시간 */
    public String endTime;
    /* 팀 소개 */
    public String introduction;
}
