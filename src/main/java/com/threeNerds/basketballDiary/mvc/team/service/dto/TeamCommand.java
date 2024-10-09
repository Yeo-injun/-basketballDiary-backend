package com.threeNerds.basketballDiary.mvc.team.service.dto;

import com.threeNerds.basketballDiary.mvc.team.domain.Team;
import com.threeNerds.basketballDiary.mvc.team.mapper.dto.TeamRegularExerciseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamCommand {

    /* 팀장사용자Seq */
    private Long leaderUserSeq;
    /* 팀명 */
    private String teamName;
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
    /* 정기운동 목록 정보 */
    private List<TeamRegularExerciseDTO> teamRegularExercises;
    /* 팀로고 이미지 */
    private MultipartFile teamLogoImage;

    public Team createTeam( String teamLogoImagePath ) {
        LocalDate now = LocalDate.now(ZoneId.of("Asia/Seoul"));
        return Team.builder()
                .teamName(      teamName )
                .hometown(      hometown )
                .foundationYmd( foundationYmd )
                .introduction(  introduction )
                .teamImagePath( teamLogoImagePath )
                .leaderUserSeq( leaderUserSeq )
                .regDate(       now )
                .updateDate(    now )
                .build();
    }
}
