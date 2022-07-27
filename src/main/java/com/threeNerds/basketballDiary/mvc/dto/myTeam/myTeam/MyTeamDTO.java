package com.threeNerds.basketballDiary.mvc.dto.myTeam.myTeam;

import com.threeNerds.basketballDiary.mvc.domain.TeamRegularExercise;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
public class MyTeamDTO {

    /**
     * 소속팀 단건 정보
     */
    // TEAM
    /* 팀 pk */
    private Long teamSeq;
    /* 팀명 */
    private String teamName;
    /* 팀 이미지 경로 */
    private String teamImagePath;
    /* 팀 이미지 */
    private MultipartFile teamImage;
    /* 연고지 */
    private String hometown;
    /* 시도코드 */
    private String sidoCode;
    /* 시군구코드 */
    private String sigunguCode;
    /* 창단일 */
    private String foundationYmd;
    /* 팀 소개 */
    private String introduction;

    // TEAM_MEMBER
    /* 회원수 */
    private Integer totMember;

    /**
     * 정기운동 목록 정보
     */
    private List<TeamRegularExercise> teamRegularExercisesList;


    public MyTeamDTO teamSeq (Long teamSeq) {
        this.teamSeq = teamSeq;
        return this;
    }

    public MyTeamDTO teamName (String teamName) {
        this.teamName = teamName;
        return this;
    }

    public MyTeamDTO teamImagePath (String teamImagePath) {
        this.teamImagePath = teamImagePath;
        return this;
    }

    public MyTeamDTO teamImage (MultipartFile teamImage) {
        this.teamImage = teamImage;
        return this;
    }

    public MyTeamDTO hometown (String hometown) {
        this.hometown = hometown;
        return this;
    }

    public MyTeamDTO sidoCode (String sidoCode) {
        this.sidoCode = sidoCode;
        return this;
    }

    public MyTeamDTO sigunguCode (String sigunguCode) {
        this.sigunguCode = sigunguCode;
        return this;
    }

    public MyTeamDTO foundationYmd (String foundationYmd) {
        this.foundationYmd = foundationYmd;
        return this;
    }

    public MyTeamDTO introduction (String introduction) {
        this.introduction = introduction;
        return this;
    }

    public MyTeamDTO totMember (Integer totMember) {
        this.totMember = totMember;
        return this;
    }

    public MyTeamDTO teamRegularExercisesList (List<TeamRegularExercise> teamRegularExercisesList) {
        this.teamRegularExercisesList = teamRegularExercisesList;
        return this;
    }
}
