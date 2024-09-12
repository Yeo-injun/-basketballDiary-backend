package com.threeNerds.basketballDiary.mvc.myTeam.dto;

import com.threeNerds.basketballDiary.mvc.team.domain.TeamRegularExercise;
import com.threeNerds.basketballDiary.mvc.team.dto.TeamRegularExerciseDTO;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MyTeamDTO {

    /** 페이징 처리를 위한 속성 */
    private Integer totalCount;

    /**
     * 소속팀 단건 정보
     */
    // TEAM
    /* 팀 pk */
    @NotNull private Long teamSeq;
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
    private List<TeamRegularExerciseDTO> teamRegularExercises;

    public void setTeamRegularExercises( List<TeamRegularExerciseDTO> teamRegularExercises ) {
        this.teamRegularExercises = teamRegularExercises;
    }
}
