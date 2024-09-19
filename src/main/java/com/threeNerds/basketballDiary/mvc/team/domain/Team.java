package com.threeNerds.basketballDiary.mvc.team.domain;

import com.threeNerds.basketballDiary.constant.code.type.HomeAwayCode;
import com.threeNerds.basketballDiary.mvc.game.domain.GameJoinTeam;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.TeamInfoDTO;
import com.threeNerds.basketballDiary.mvc.team.controller.request.RegisterTeamRequest;
import lombok.*;

import java.time.LocalDate;
import java.time.ZoneId;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Team {
    // 팀SEQ
    private Long teamSeq;

    // 팀장ID
    private Long leaderUserSeq;

    // 팀명
    private String teamName;

    // 연고지
    private String hometown;

    // 소개글
    private String introduction;

    // 창단일자
    private String foundationYmd;

    // 등록일자
    private LocalDate regDate;

    // 수정일자
    private LocalDate updateDate;

    // 시도코드
    private String sidoCode;

    // 시군구코드
    private String sigunguCode;

    // 팀 이미지 경로
    private String teamImagePath;

    public static Team create( RegisterTeamRequest teamDTO, String teamImagePath ) {
        LocalDate now = LocalDate.now(ZoneId.of("Asia/Seoul"));
        return Team.builder()
                    .teamName(      teamDTO.getTeamName() )
                    .hometown(      teamDTO.getHometown() )
                    .foundationYmd( teamDTO.getFoundationYmd() )
                    .introduction(  teamDTO.getIntroduction() )
                    .teamImagePath( teamImagePath )
                    .leaderUserSeq( teamDTO.getLeaderUserSeq() )
                    .regDate(       now )
                    .updateDate(    now )
                    .build();
    }

    public Team ofUpdate( TeamInfoDTO teamInfo, String teamImagePath ) {
        String imagePath = "".equals( teamImagePath ) ? this.teamImagePath : teamImagePath;
        return Team.builder()
                .teamSeq(       this.teamSeq )
                .leaderUserSeq( this.leaderUserSeq )
                .teamName(      teamInfo.getTeamName() )
                .teamImagePath( imagePath )
                .hometown(      teamInfo.getHometown() )
                .introduction(  teamInfo.getIntroduction() )
                .foundationYmd( teamInfo.getFoundationYmd() )
                .regDate(       this.regDate )
                .updateDate(    LocalDate.now( ZoneId.of("Asia/Seoul" ) ) )
                .sidoCode(      teamInfo.getSidoCode() )
                .sigunguCode(   teamInfo.getSigunguCode() )
                .build();
    }

    public GameJoinTeam joinGameAsHome( Long gameSeq ) {
        return GameJoinTeam.builder()
                .gameSeq(       gameSeq )
                .teamSeq(       this.teamSeq )
                .teamName(      this.teamName )
                .homeAwayCode(  HomeAwayCode.HOME_TEAM.getCode() )
                .build();
    }

    public GameJoinTeam joinGameAsAway( Long gameSeq ) {
        return GameJoinTeam.builder()
                .gameSeq(       gameSeq )
                .teamSeq(       this.teamSeq )
                .teamName(      this.teamName )
                .homeAwayCode(  HomeAwayCode.AWAY_TEAM.getCode() )
                .build();
    }

}
