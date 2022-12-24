package com.threeNerds.basketballDiary.mvc.team.dto;

import lombok.Getter;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class TeamDTO {
    /* 페이징을 위한 총 row갯수 */
    private Integer totalCount;
    /* 정기운동 목록 */
    private List<TeamRegularExerciseDTO> teamRegularExercises;

    /* 팀 정보 */
    private Long teamSeq;
    private Long leaderUserSeq;
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


    public TeamDTO teamSeq (Long teamSeq) {
        this.teamSeq = teamSeq;
        return this;
    }

    public TeamDTO leaderUserSeq(Long leaderUserSeq){
        this.leaderUserSeq = leaderUserSeq;
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
     * >> SearchOppenentsDTO, MyTeamDTO -- 추상클래스를 상속받아 구현된 메소드를 상속받는 것은 어떤지??
     * >> 상속은 is-A관계에서 주로 사용. 따라서 다른 방법 검토 필요 참고자료 : https://jgrammer.tistory.com/entry/%EC%9D%B4%ED%8E%99%ED%8B%B0%EB%B8%8C-%EC%9E%90%EB%B0%94-%EC%83%81%EC%86%8D%EB%B3%B4%EB%8B%A4%EB%8A%94-%EC%BB%B4%ED%8F%AC%EC%A7%80%EC%85%98%EC%9D%84-%EC%82%AC%EC%9A%A9%ED%95%98%EB%9D%BC
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
