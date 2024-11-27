package com.threeNerds.basketballDiary.mvc.game.service;

import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.error.DomainErrorType;
import com.threeNerds.basketballDiary.exception.error.ErrorMessageType;
import com.threeNerds.basketballDiary.mvc.game.domain.*;
import com.threeNerds.basketballDiary.mvc.game.domain.repository.GameJoinPlayerRepository;
import com.threeNerds.basketballDiary.mvc.game.domain.repository.GameRecordAuthRepository;
import com.threeNerds.basketballDiary.mvc.game.mapper.dto.GameRecorderCandidateDTO;
import com.threeNerds.basketballDiary.mvc.game.mapper.dto.GameRecorderDTO;
import com.threeNerds.basketballDiary.mvc.game.mapper.GameRecorderMapper;
import com.threeNerds.basketballDiary.mvc.game.service.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.FORBIDDEN;

/**
 * 경기권한과 관련된 서비스를 제공한다.
 * - 경기권한은 생성자(Creator)와 기록원(Recorder)으로 구분한다.
 * - 경기생성자는 기록원의 권한을 포함하며 경기를 생성/삭제하고, 경기정보를 수정하고, 경기기록 등 경기와 관련된 모든 행위를 할 수 있다.
 * - 경기기록원은 경기기록을 입력/수정만 가능하다.
    TODO [ 검토사항 ] 경기권한 테이블을 제거하고, 경기참가선수 테이블에 경기권한 컬럼을 추가하여 경기기록관리하는 방식 검토
        >> 변경에 따른 이점 : 경기기록권한을 가질 수 있는 대상이 경기참가선수로 제한됨 ( 물리적 구조변경에 의해서 )
        >> 단점 : 확장성 없음...
 */
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class GameAuthService {

    /**
     * Repository
     */
    private final GameJoinPlayerRepository gameJoinPlayerRepo;
    private final GameRecordAuthRepository gameRecordAuthRepo;

    /**
     * Mapper
     */
    private final GameRecorderMapper gameRecorderRepo;

    /**
     * 2022.01.04
     * 경기기록원 조회 ( 경기 생성자, 경기 기록원 등 )
     * @author 이성주
     */
    public GameRecorderQuery.Result getGameRecorders( GameRecorderQuery query ) {
        List<GameRecorderDTO> recorders = gameRecorderRepo.findAllRecorders( query.getGameSeq() );
        return query.buildResult( recorders );
    }

    /**
     * 2022.04.25
     * 경기참가선수에게 경기 기록원으로 권한 부여 ( 단건 )
     * @author 여인준
     */
    public void saveGameRecorder( GameRecorderCommand command ) {
        Long gameSeq                        = command.getGameSeq();
        GameRecorderDTO gameRecorderInput   = command.getGameRecorder();
        Long gameJoinPlayerSeq              = gameRecorderInput.getGameJoinPlayerSeq();

        GameJoinPlayer playerParam = GameJoinPlayer.builder()
                                        .gameJoinPlayerSeq( gameJoinPlayerSeq )
                                        .build();
        GameJoinPlayer player = gameJoinPlayerRepo.findPlayer( playerParam );
        // 경기참가선수인지 확인
        if ( null == player ) {
            throw new CustomException( DomainErrorType.NO_EXIST_GAME_JOIN_PLAYER );
        }
        if ( !player.isJoinInGame( gameSeq ) ) {
            throw new CustomException( DomainErrorType.ONLY_RECORD_BY_GAME_JOIN_PLAYER );
        }
        // 경기기록권한을 이미 가지고 있는지 확인
        List<GameAuth> permittedGameRecoreders = gameRecordAuthRepo.findAllAuthByGameSeq( gameSeq );
        if ( player.hasGameRecordAuth( permittedGameRecoreders )) {
            throw new CustomException( DomainErrorType.ALREADY_EXIST_RECORD_AUTH );
        }
        // 경기기록권한 부여
        GameAuth gameRecorder = player.toGameRecorder();
        gameRecordAuthRepo.saveGameAuth( gameRecorder );
    }

    public void deleteGameRecorder( GameRecorderCommand command ) {
        Long gameSeq              = command.getGameSeq();
        Long gameRecordAuthSeq    = command.getGameRecordAuthSeq();
        GameAuth authParam = GameAuth.builder()
                                    .gameRecordAuthSeq( gameRecordAuthSeq )
                                    .build();
        GameAuth deleteAuth = gameRecordAuthRepo.findAuth( authParam );
        GameAuth.DeleteStatus deleteStatus = deleteAuth.enableDelete( gameSeq );
        if ( !deleteStatus.isEnable() ) {
            throw new CustomException( new ErrorMessageType() {
                @Override
                public Integer getStatus() {
                    return FORBIDDEN.value();
                }

                @Override
                public String getCode() {
                    return null;
                }

                @Override
                public String getMessage() {
                    return deleteStatus.getMessage();
                }
            });
        }
        gameRecordAuthRepo.deleteGameAuthByAuthSeq( gameRecordAuthSeq );
    }

    public void createCreatorAuth( GameAuthCommand command ) {
        /** 경기기록 권한 부여 - 생성자로 */
        GameAuth gameCreator = GameAuth.ofCreator( command.getGameSeq(), command.getUserSeq(), command.getGameJoinPlayerSeq() );
        gameRecordAuthRepo.saveGameAuth( gameCreator );
    }

    /**
     * 2024.02.24
     * 사용자의 경기권한 정보 조회
     * - 사용자가 가지고 있는 경기권한을 권한 수준 별로 조회한다.
     * cf. 로그인시 사용자의 경기별로 권한수준이 매핑된 Map객체를 Session에 보관한다.
     * @author 여인준
     */
    public GameAuthQuery.Result getGameAuthInfo( GameAuthQuery query ) {
        Map< String, String > authGames = gameRecordAuthRepo.findAllAuthList( query.getUserSeq() )
                .stream()
                .collect( Collectors.toMap(
                            item -> String.valueOf( item.getGameSeq() ),
                            GameAuth::getGameRecordAuthCode
                ));
        return query.buildResult( authGames );
    }

    /**
     * 경기기록원 후보 조회
     * - 경기 기록권한을 부여받을 수 있는 대상을 조회한다.
     * - 경기 기록원은 다음 요건을 충족해야 한다.
     *   1) 서비스 회원이어야 한다.
     *   2) 경기에 참가한 선수여야 한다.
     */
    public GameRecorderCandidatesQuery.Result getGameRecorderCandidates( GameRecorderCandidatesQuery query ) {

        GameRecorderCandidateDTO searchCond = new GameRecorderCandidateDTO()
                .gameSeq(       query.getGameSeq() )
                .homeAwayCode(  query.getHomeAwayCode() );

        return query.buildResult( gameRecorderRepo.findAllCandidates( searchCond ) );
    }
}