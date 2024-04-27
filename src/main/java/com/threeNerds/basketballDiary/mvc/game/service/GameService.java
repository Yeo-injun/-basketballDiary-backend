package com.threeNerds.basketballDiary.mvc.game.service;

import com.threeNerds.basketballDiary.constant.code.type.GameRecordStateCode;
import com.threeNerds.basketballDiary.exception.error.DomainErrorType;
import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.mvc.game.controller.response.GetGameBasicInfoResponse;
import com.threeNerds.basketballDiary.mvc.game.domain.Game;
import com.threeNerds.basketballDiary.mvc.game.domain.GameAuth;
import com.threeNerds.basketballDiary.mvc.game.domain.GameJoinTeam;
import com.threeNerds.basketballDiary.mvc.game.repository.GameJoinTeamRepository;
import com.threeNerds.basketballDiary.mvc.game.repository.GameRecordAuthRepository;
import com.threeNerds.basketballDiary.mvc.game.service.dto.GameCreationCommand;
import com.threeNerds.basketballDiary.mvc.myTeam.domain.TeamMember;
import com.threeNerds.basketballDiary.mvc.game.repository.GameRepository;
import com.threeNerds.basketballDiary.mvc.myTeam.repository.TeamMemberRepository;
import com.threeNerds.basketballDiary.mvc.team.domain.Team;
import com.threeNerds.basketballDiary.mvc.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class GameService {

    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;

    private final GameRepository gameRepository;
    private final GameJoinTeamRepository gameJoinTeamRepository;
    private final GameRecordAuthRepository gameRecordAuthRepo;

    public void deleteGame( Long gameSeq ) {
        boolean isDeleteGame = gameRepository.deleteGame(gameSeq) > 0;
        if ( !isDeleteGame ) {
            throw new CustomException( DomainErrorType.NOT_FOUND_GAME_FOR_DELETE );
        }
        // TODO 게임참가팀, 게임참가선수, 쿼터기록, 게임기록권한 테이블도 다 삭제해줘야 함....
    }

    /** 22.10.31
     * 게임 생성
     * @author 여인준
     **/
    public Long createGame( GameCreationCommand command ) {
        final Long userSeq = command.getUserSeq();
        final Long teamSeq = command.getTeamSeq();
        /** 게임생성 요청 사용자 검증 - 게임을 생성하는 팀에 소속되어 있는지 확인 */
        TeamMember tmParam = TeamMember.builder()
                .userSeq( userSeq )
                .teamSeq( teamSeq )
                .build();

        TeamMember tm = Optional
                            .ofNullable( teamMemberRepository.findTeamMemberByUserAndTeamSeq( tmParam ) )
                            .orElseThrow( ()-> new CustomException( DomainErrorType.ONLY_TEAM_MEMBER_HANDLE ) );

        /** 게임 생성 */
        Game newGame = Game.of( tm.getTeamMemberSeq(), command );
        gameRepository.saveGame( newGame );
        Long newGameSeq = newGame.getGameSeq();

        /** 게임참가팀 - HOME팀 생성 */
        Team team = Optional
                        .ofNullable( teamRepository.findByTeamSeq( teamSeq ) )
                        .orElseThrow( ()-> new CustomException( DomainErrorType.TEAM_NOT_FOUND ) );
        GameJoinTeam homeTeam = team.joinGameAsHome( newGameSeq );
        homeTeam.inSelfGame();
        gameJoinTeamRepository.saveGameJoinTeam( homeTeam );

        // TODO 경기참가선수로 반영
        Long gameJoinPlayerSeq = 0L; // TODO 임시로 반영

        /** 경기기록 권한 부여 - 생성자로 */
        // TODO 해당 메소드의 서비스위치 판단
        GameAuth gameCreator = GameAuth.ofCreator( newGameSeq, userSeq, gameJoinPlayerSeq );
        gameRecordAuthRepo.saveGameAuth( gameCreator );

        /** 생성된 게임Seq 반환 */
        return newGameSeq;
    }

    /**
     * 22.11.12
     * 게임기록 상세조회
     * @author 이성주
     */
    public GetGameBasicInfoResponse getGameInfo( Long gameSeq ) {
        Game game = Optional
                        .ofNullable( gameRepository.findGame( gameSeq ) )
                        .orElseThrow( () -> new CustomException( DomainErrorType.NOT_FOUND_GAME ) );
        return new GetGameBasicInfoResponse( game );
    }

    /**
     * 22.12.12
     * 게임 확정
     * @author 이성주
     * - 23.10.26 인준 : 경기상태 변경시 게임기록상태코드 확인해서 exception처리
     */
    public void confirmGame( Long gameSeq ) {
        /** 해당 경기의 게임기록상태코드 확인 */
        Game game = gameRepository.findGame( gameSeq );
        if ( game.isConfirmed() ) {
            throw new CustomException( DomainErrorType.ALREADY_GAME_CONFIRMED );
        }

        if ( !game.canUpdateRecord() ) {
            throw new CustomException( DomainErrorType.CANT_UPDATE_GAME_CONFIRMATION );
        }

        /** 게임기록상태코드 변경 - >> 게임확정(03) */
        gameRepository.updateGameRecordState(
            Game.builder()
                .gameSeq(               gameSeq )
                .gameRecordStateCode(   GameRecordStateCode.CONFIRMATION.getCode() )
                .build()
        );
    }
}
