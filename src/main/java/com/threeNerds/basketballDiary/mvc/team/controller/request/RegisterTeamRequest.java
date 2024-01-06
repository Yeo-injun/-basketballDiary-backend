package com.threeNerds.basketballDiary.mvc.team.controller.request;

import com.threeNerds.basketballDiary.mvc.team.dto.TeamRegularExerciseDTO;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
public class RegisterTeamRequest {

    /* 팀장사용자Seq */
    private Long leaderUserSeq;
    /* 팀명 */
    @NotEmpty( message = "팀명은 필수 입력항목입니다.")
    private String teamName;
    /* 연고지 */
    @NotEmpty( message = "연고지는 필수 입력항목입니다.")
    private String hometown;
    /* 시도코드 */
    @NotEmpty( message = "시도코드는 필수 입력항목입니다.")
    private String sidoCode;
    /* 시군구코드 */
    @NotEmpty( message = "시군구코드는 필수 입력항목입니다.")
    private String sigunguCode;
    /* 창단일 */
    @NotEmpty( message = "창단일은 필수 입력항목입니다.")
    private String foundationYmd;
    /* 팀 소개 */
    private String introduction;
    /* 정기운동 목록 정보 */
    private List<TeamRegularExerciseDTO> teamRegularExercises;
    /* 팀로고 이미지 */
    private MultipartFile teamLogoImage;

    /**
     * 기본생성자 선언 - MessageBinding시 Jackson 라이브러리 정책 때문에 선언
     */
    public RegisterTeamRequest() {

    }

    public RegisterTeamRequest( Long userSeq, RegisterTeamRequest reqBody, MultipartFile teamLogoImage ) {
        this.leaderUserSeq  = userSeq;
        this.teamName       = reqBody.getTeamName();
        this.hometown       = reqBody.getHometown();
        this.sidoCode       = reqBody.getSidoCode();
        this.sigunguCode    = reqBody.getSigunguCode();
        this.foundationYmd  = reqBody.getFoundationYmd();
        this.introduction   = reqBody.getIntroduction();
        this.teamRegularExercises = reqBody.getTeamRegularExercises();
        this.teamLogoImage  = teamLogoImage;
    }
}
