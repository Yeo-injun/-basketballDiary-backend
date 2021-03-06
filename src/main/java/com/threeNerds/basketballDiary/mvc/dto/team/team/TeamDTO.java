package com.threeNerds.basketballDiary.mvc.dto.team.team;

import com.threeNerds.basketballDiary.mvc.domain.TeamRegularExercise;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class TeamDTO {

    /* 팀 정보 */
    private Long teamSeq;

    private String leaderId;

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
    private List<TeamRegularExercise> teamRegularExercisesList;

    public TeamDTO teamSeq (Long teamSeq) {
        this.teamSeq = teamSeq;
        return this;
    }

    public TeamDTO leaderId(String leaderId){
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

    public TeamDTO teamRegularExercisesList (List<TeamRegularExercise> teamRegularExercisesList) {
        this.teamRegularExercisesList = teamRegularExercisesList;
        return this;
    }
}
