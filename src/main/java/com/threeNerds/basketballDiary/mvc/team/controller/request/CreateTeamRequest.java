package com.threeNerds.basketballDiary.mvc.team.controller.request;

import com.threeNerds.basketballDiary.mvc.team.dto.TeamRegularExerciseDTO;
import com.threeNerds.basketballDiary.mvc.team.service.dto.TeamCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.util.Collections;
import java.util.List;

@Getter
@NoArgsConstructor
public class CreateTeamRequest {

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


    public TeamCommand toCommand( Long userSeq, MultipartFile teamLogoImage ) {
        return TeamCommand.builder()
                .leaderUserSeq( userSeq )
                .teamName( teamName )
                .hometown( hometown )
                .sidoCode( sidoCode )
                .sigunguCode( sigunguCode )
                .foundationYmd( foundationYmd )
                .introduction( introduction )
                .teamRegularExercises( null == teamRegularExercises ? Collections.emptyList() : teamRegularExercises )
                .teamLogoImage( teamLogoImage )
                .build();
    }
}
