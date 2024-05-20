package com.threeNerds.basketballDiary.mvc.game.domain;

import com.threeNerds.basketballDiary.constant.code.type.HomeAwayCode;
import com.threeNerds.basketballDiary.constant.code.type.PlayerTypeCode;
import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.error.DomainErrorType;
import com.threeNerds.basketballDiary.mvc.myTeam.domain.TeamMember;
import com.threeNerds.basketballDiary.mvc.user.domain.User;
import com.threeNerds.basketballDiary.mvc.game.dto.GameJoinPlayerDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

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

    /**
     * 선수목록에서 제외가능한지
     * @return
     */
    public boolean isRemovable() {
        // TODO GameJoinPlayer 테이블에 경기입력권한 속성 추가하여 관리
        // 임시 정책 반영 선수유형코드가 팀원이면 명단에서 제거할 수 없음
        // 본 정책은 경기기록 권한을 가지고 있으면 제거 못하게 해야 함 ( Recorder 혹은 Creator )
        return !PlayerTypeCode.TEAM_MEMBER.getCode().equals( this.playerTypeCode );
    }

    public boolean isAppendable( List<GameJoinPlayer> registeredPlayers ) {
        /** 중복된 등번호가 있는지 체크하기 */
        for ( GameJoinPlayer player : registeredPlayers ) {
            if ( player.getBackNumber().equals( this.backNumber ) ) {
                throw new CustomException( DomainErrorType.DUPLICATE_BACK_NUMBER );
            }
            if ( player.getEmail().equals( this.email ) ) {
                throw new CustomException( DomainErrorType.DUPLICATE_EMAIL );
            }
            // 비회원 게스트의 경우 userSeq가 존재하지 않아서 비교불가. 예외처리
            // cf. null 끼리 비교하면 true로 처리되어 계속 Exception발생함  
            if ( !PlayerTypeCode.UNAUTH_GUEST.getCode().equals( this.playerTypeCode )
                 && Objects.equals( player.getUserSeq(), this.userSeq ) ) {
                throw new CustomException( DomainErrorType.ALREADY_EXIST_JOIN_PLAYER );
            }

        }
        return true;
    }

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

    public static GameJoinPlayer ofAuthPlayer(
            GameJoinTeam gameJoinTeam, User user, String playerTypeCode, String backNumber
    ) {
        return GameJoinPlayer.builder()
                .gameJoinTeamSeq(   gameJoinTeam.getGameJoinTeamSeq() )
                .gameSeq(           gameJoinTeam.getGameSeq() )
                .homeAwayCode(      gameJoinTeam.getHomeAwayCode() )
                .playerTypeCode(    playerTypeCode )
                .userSeq(           user.getUserSeq() )
                .name(              user.getName() )
                .backNumber(        backNumber )
                .positionCode(      user.getPositionCode() )
                .email(             user.getEmail() )
                .build();
    }

    public static GameJoinPlayer ofUnauthPlayer(
            GameJoinTeam gameJoinTeam, String userName, String backNumber, String positionCode, String email
    ) {
        return GameJoinPlayer.builder()
                .gameJoinTeamSeq(   gameJoinTeam.getGameJoinTeamSeq() )
                .gameSeq(           gameJoinTeam.getGameSeq() )
                .homeAwayCode(      gameJoinTeam.getHomeAwayCode() )
                .playerTypeCode(    PlayerTypeCode.UNAUTH_GUEST.getCode() )
                .userSeq(           null )
                .name(              userName )
                .backNumber(        backNumber )
                .positionCode(      positionCode )
                .email(             email )
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
