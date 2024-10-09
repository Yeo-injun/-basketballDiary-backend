package com.threeNerds.basketballDiary.mvc.game.service;

import com.threeNerds.basketballDiary.constant.code.type.GameRecordStateCode;
import com.threeNerds.basketballDiary.exception.error.DomainErrorType;
import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.mvc.game.domain.Game;

import com.threeNerds.basketballDiary.mvc.game.domain.repository.*;
import com.threeNerds.basketballDiary.mvc.game.mapper.dto.GameDetailDTO;
import com.threeNerds.basketballDiary.mvc.game.service.dto.GameCreationCommand;
import com.threeNerds.basketballDiary.mvc.game.service.dto.GameQuery;
import com.threeNerds.basketballDiary.mvc.team.domain.TeamMember;
import com.threeNerds.basketballDiary.mvc.team.domain.repository.TeamMemberRepository;
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

    private final GameRepository gameRepo;
    private final GameJoinPlayerRepository gameJoinPlayerRepo;
    private final GameJoinTeamRepository gameJoinTeamRepo;
    private final QuarterPlayerRecordsRepository quarterPlayerRecordsRepo;
    private final QuarterTeamRecordsRepository quarterTeamRecordsRepo;

    private final TeamMemberRepository teamMemberRepository;



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
                            .orElseThrow( ()-> new CustomException( DomainErrorType.ONLY_CREATE_GAME_BY_TEAM_MEMBER ) );

        /** 게임 생성 */
        Game newGame = Game.of( tm.getTeamMemberSeq(), command );
        gameRepo.saveGame( newGame );
        return newGame.getGameSeq();
    }

    public void deleteGame( Long gameSeq ) {
        boolean isDeleteGame = gameRepo.deleteGame( gameSeq ) > 0;
        if ( !isDeleteGame ) {
            throw new CustomException( DomainErrorType.NOT_FOUND_GAME_FOR_DELETE );
        }
        // TODO 게임기록권한 테이블은 삭제하지 않음. 테이블 자체를 없앨 예정이기 때문.
        gameJoinTeamRepo.deleteByGame( gameSeq );
        gameJoinPlayerRepo.deleteByGame( gameSeq );
        quarterTeamRecordsRepo.deleteByGame( gameSeq );
        quarterPlayerRecordsRepo.deleteByGame( gameSeq );
    }

    /**
     * 22.11.12
     * 경기 기초 정보 조회
     * @author 이성주
     */
    public GameQuery.Result getGameBasicInfo( GameQuery query ) {
        Game game = gameRepo.findGame( query.getGameSeq() );
        if ( null == game ) {
            throw new CustomException( DomainErrorType.NOT_FOUND_GAME );
        }
        return query.buildResult( GameDetailDTO.of( game ) );
    }

    /**
     * 22.12.12
     * 게임 확정
     * @author 이성주
     * - 23.10.26 인준 : 경기상태 변경시 게임기록상태코드 확인해서 exception처리
     */
    public void confirmGame( Long gameSeq ) {
        /** 해당 경기의 게임기록상태코드 확인 */
        Game game = gameRepo.findGame( gameSeq );
        if ( game.isConfirmed() ) {
            throw new CustomException( DomainErrorType.ALREADY_GAME_CONFIRMED );
        }

        if ( !game.canUpdateRecord() ) {
            throw new CustomException( DomainErrorType.CANT_UPDATE_GAME_CONFIRMATION );
        }

        /** 게임기록상태코드 변경 - >> 게임확정(03) */
        gameRepo.updateGameRecordState(
            Game.builder()
                .gameSeq(               gameSeq )
                .gameRecordStateCode(   GameRecordStateCode.CONFIRMATION.getCode() )
                .build()
        );
    }
}
