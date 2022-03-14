package com.threeNerds.basketballDiary.mvc.dto.team.team;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class TeamInfoDTO {

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


    public TeamInfoDTO teamSeq (Long teamSeq) {
        this.teamSeq = teamSeq;
        return this;
    }

    public TeamInfoDTO leaderId(String leaderId){
        this.leaderId = leaderId;
        return this;
    }

    public TeamInfoDTO teamName(String teamName){
        this.teamName = teamName;
        return this;
    }

    public TeamInfoDTO teamImagePath(String teamImagePath){
        this.teamImagePath = teamImagePath;
        return this;
    }

    public TeamInfoDTO hometown(String hometown){
        this.hometown = hometown;
        return this;
    }
    public TeamInfoDTO introduction(String introduction){
        this.introduction = introduction;
        return this;
    }

    public TeamInfoDTO foundationYmd(String foundationYmd){
        this.foundationYmd = foundationYmd;
        return this;
    }

    public TeamInfoDTO regDate(LocalDate regDate){
        this.regDate = regDate;
        return this;
    }

    public TeamInfoDTO updateDate(LocalDate updateDate){
        this.updateDate = updateDate;
        return this;
    }

    public TeamInfoDTO sidoCode(String sidoCode){
        this.sidoCode = sidoCode;
        return this;
    }

    public TeamInfoDTO sigunguCode(String sigunguCode){
        this.sigunguCode = sigunguCode;
        return this;
    }

    public TeamInfoDTO totMember(Integer totMember){
        this.totMember = totMember;
        return this;
    }
}

