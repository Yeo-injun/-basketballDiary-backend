package com.threeNerds.basketballDiary.mvc.team.mapper.dto;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
public class TeamDTO {
    /* 페이징을 위한 총 row갯수 */
    private Integer totalCount;
    /* 정기운동 목록 */
    private List<TeamRegularExerciseDTO> teamRegularExercises;

    /* 팀 정보 */
    private Long teamSeq;
    private Long leaderUserSeq;
    @NotEmpty
    private String teamName;
    private String teamImagePath;
    @NotEmpty
    private String hometown;
    private String introduction;
    private String foundationYmd;
    private LocalDate regDate;
    private LocalDate updateDate;
    @NotNull
    private String sidoCode;
    @NotEmpty
    private String sigunguCode;
    private Integer totMember;

    public void setTeamRegularExercises ( List<TeamRegularExerciseDTO> exercises ) {
        this.teamRegularExercises = exercises;
    }

}
