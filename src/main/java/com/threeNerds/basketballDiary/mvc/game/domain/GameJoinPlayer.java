package com.threeNerds.basketballDiary.mvc.game.domain;

import com.threeNerds.basketballDiary.constant.code.type.HomeAwayCode;
import com.threeNerds.basketballDiary.constant.code.type.PlayerTypeCode;
import com.threeNerds.basketballDiary.mvc.myTeam.domain.TeamMember;
import com.threeNerds.basketballDiary.mvc.user.domain.User;
import com.threeNerds.basketballDiary.mvc.game.dto.GameJoinPlayerDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameJoinPlayer {

    private Long gameJoinPlayerSeq;     // 게임참가선수Seq
    private Long gameSeq;               // 게임Seq
    private String homeAwayCode;        // 홈어웨이코드
    private Long gameJoinTeamSeq;       // 게임참가팀Seq
    private String playerTypeCode;      // 선수유형코드
    private Long userSeq;               // 사용자Seq
    private String name;                // 이름
    private String backNumber;          // 등번호
    private String positionCode;        // 포지션코드
    private String email;               // 이메일

    public static GameJoinPlayer ofCreator(
        Long gameSeq, Long gameJoinTeamSeq,
        User user, TeamMember teamMember
    ) {
        return GameJoinPlayer.builder()
                .gameSeq(gameSeq)
                .homeAwayCode(HomeAwayCode.HOME_TEAM.getCode())
                .gameJoinTeamSeq( gameJoinTeamSeq )
                .playerTypeCode(PlayerTypeCode.TEAM_MEMBER.getCode())
                .userSeq(user.getUserSeq())
                .name(user.getName())
                .backNumber( teamMember.getBackNumber() )
                .positionCode( user.getPositionCode() )
                .email( user.getEmail() )
                .build();
    }

    public static GameJoinPlayer createInqParam( Long gameSeq, String homeAwayCode ) {
        return GameJoinPlayer.builder()
                .gameSeq( gameSeq )
                .homeAwayCode( homeAwayCode )
                .build();
    }
    public static GameJoinPlayer createUnauthPlayer(
            GameJoinTeam gameJoinTeam,
            GameJoinPlayerDTO joinPlayerDTO
    ) {
        return create( gameJoinTeam.getGameJoinTeamSeq(),
                        gameJoinTeam.getGameSeq(),
                        gameJoinTeam.getHomeAwayCode(),
                        PlayerTypeCode.UNAUTH_GUEST.getCode(),
                        null,
                        joinPlayerDTO.getUserName(),
                        joinPlayerDTO.getBackNumber(),
                        joinPlayerDTO.getPositionCode(),
                        joinPlayerDTO.getEmail());
    }

    public static GameJoinPlayer createAuthPlayer(
            GameJoinTeam gameJoinTeam,
            String playerTypeCode, String backNumber, User user
    ) {
        return create(gameJoinTeam.getGameJoinTeamSeq(),
                gameJoinTeam.getGameSeq(),
                gameJoinTeam.getHomeAwayCode(),
                playerTypeCode,
                user.getUserSeq(),
                user.getName(),
                backNumber,
                user.getPositionCode(),
                user.getEmail());
    }

    private static GameJoinPlayer create(
            Long gameJoinTeamSeq, Long gameSeq, String homeAwayCode,
            String playerTypeCode, Long userSeq, String name,
            String backNumber, String positionCode, String email
    ) {
        return GameJoinPlayer.builder()
                .gameJoinTeamSeq( gameJoinTeamSeq )
                .gameSeq( gameSeq )
                .homeAwayCode( homeAwayCode )
                .playerTypeCode( playerTypeCode )
                .userSeq( userSeq )
                .name( name )
                .backNumber( backNumber )
                .positionCode( positionCode )
                .email( email )
                .build();
    }
}
