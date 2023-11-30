package com.threeNerds.basketballDiary.mvc.myTeam.dto;

import com.threeNerds.basketballDiary.mvc.team.dto.TeamRegularExerciseDTO;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class TeamInfoDTO {

    private Long teamSeq; /* 팀 pk */
    /* 팀명 */
    private String teamName;
    /* 팀 이미지 경로 */
    private String teamImagePath;
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
}
