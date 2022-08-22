package com.threeNerds.basketballDiary.mvc.dto.team.team;

import com.threeNerds.basketballDiary.mvc.domain.TeamRegularExercise;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class TeamDTO {
    /* 페이징을 위한 총 row갯수 */
    private Integer totalCount;

    /* 팀 정보 */
    private Long teamSeq;
    private Long leaderId;
    private String teamName;
    private String teamImagePath;
    private String hometown;
    private String introduction;
    private String foundationYmd;
    private LocalDate regDate;
    private LocalDate updateDate;
    private String sidoCode;
    private String sigunguCode;
    private Integer totMember;

    /* 정기운동 목록 */
    private List<TeamRegularExerciseDTO> teamRegularExercises;

    public TeamDTO teamSeq (Long teamSeq) {
        this.teamSeq = teamSeq;
        return this;
    }

    public TeamDTO leaderId(Long leaderId){
        this.leaderId = leaderId;
        return this;
    }

    public TeamDTO teamName(String teamName){
        this.teamName = teamName;
        return this;
    }

    public TeamDTO teamImagePath(String teamImagePath){
        this.teamImagePath = teamImagePath;
        return this;
    }

    public TeamDTO hometown(String hometown){
        this.hometown = hometown;
        return this;
    }
    public TeamDTO introduction(String introduction){
        this.introduction = introduction;
        return this;
    }

    public TeamDTO foundationYmd(String foundationYmd){
        this.foundationYmd = foundationYmd;
        return this;
    }

    public TeamDTO regDate(LocalDate regDate){
        this.regDate = regDate;
        return this;
    }

    public TeamDTO updateDate(LocalDate updateDate){
        this.updateDate = updateDate;
        return this;
    }

    public TeamDTO sidoCode(String sidoCode){
        this.sidoCode = sidoCode;
        return this;
    }

    public TeamDTO sigunguCode(String sigunguCode){
        this.sigunguCode = sigunguCode;
        return this;
    }

    public TeamDTO totMember(Integer totMember){
        this.totMember = totMember;
        return this;
    }

    public TeamDTO teamRegularExercises (List<TeamRegularExerciseDTO> exercisesDTO)
    {
        this.teamRegularExercises = exercisesDTO;
        return this;
    }

    /** TODO 코드 중복 제거 : 같은 내용의 코드가 2곳 이상 작성되어 있음
     * >> TeamDTO, MyTeamDTO -- 추상클래스를 상속받아 구현된 메소드를 상속받는 것은 어떤지??
     */
    public TeamDTO setParsedTeamRegularExercises (List<TeamRegularExerciseDTO> exercisesDTO)
    {
        if (exercisesDTO.isEmpty()) {
            this.teamRegularExercises = Collections.emptyList();
            return this;
        }

        exercisesDTO.stream()
                .map(TeamRegularExerciseDTO::dayOfTheWeekCodeName)
                .collect(Collectors.toList());

        this.teamRegularExercises = exercisesDTO;
        return this;
    }
}
