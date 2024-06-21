package com.threeNerds.basketballDiary.mvc.game.service;

import com.threeNerds.basketballDiary.auth.constant.AuthLevel;
import com.threeNerds.basketballDiary.auth.constant.AuthType;
import com.threeNerds.basketballDiary.constant.code.type.GameRecordAuthCode;
import com.threeNerds.basketballDiary.mvc.game.domain.*;
import com.threeNerds.basketballDiary.mvc.game.dto.GameRecorderCandidateDTO;
import com.threeNerds.basketballDiary.mvc.game.dto.GameRecorderDTO;
import com.threeNerds.basketballDiary.mvc.game.repository.*;
import com.threeNerds.basketballDiary.mvc.game.repository.dto.GameRecorderRepository;
import com.threeNerds.basketballDiary.mvc.game.service.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 경기권한과 관련된 서비스를 제공한다.
 * - 경기권한은 생성자(Creator)와 기록원(Recorder)으로 구분한다.
 * - 경기생성자는 기록원의 권한을 포함하며 경기를 생성/삭제하고, 경기정보를 수정하고, 경기기록 등 경기와 관련된 모든 행위를 할 수 있다.
 * - 경기기록원은 경기기록을 입력/수정만 가능하다.
 */
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class GameAuthService {

    private final GameRecordAuthRepository gameRecordAuthRepo;
    private final GameRecorderRepository gameRecorderRepo;

    /**
     * 2022.01.04
     * 경기 관리자 조회 ( 경기 생성자, 경기 기록원 등 )
     * @author 이성주
     */
    // TODO 경기권한 테이블을 참조하지 않고 경기참가선수 테이블에 경기권한 컬럼을 추가하여 경기기록원을 조회
    // TODO 현재 양쪽팀 모두에 경기참가선수 반영이 가능하여 기록권한을 부여하면 기록원이 2명으로 조회되는 오류 존재.
    // TODO 해당 오류를 해결하기 위해 테이블의 컬럼속성으로 기록원 관리하도록 변경 예정
    public GameRecorderQuery.Result getGameRecorders( GameRecorderQuery query ) {
        List<GameRecorderDTO> recorders = gameRecorderRepo.findAllRecorders( query.getGameSeq() );
        return query.buildResult( recorders );
    }

    /**
     * 2022.04.25
     * 경기 기록원으로 권한 부여
     * - 기존 경기기록원 권한 정보 전체 삭제
     * - 화면에서 전달받은 기록원 목록 정보 insert
     * @author 여인준
     */
    // TODO 경기권한 테이블을 참조하지 않고 경기참가선수 테이블에 경기권한 컬럼을 추가하여 경기기록원 임명하도록 수정
    public void saveGameRecorders( GameRecorderCommand command ) {
        Long gameSeq                        = command.getGameSeq();
        List<GameRecorderDTO> gameRecorders = command.getGameRecorders();


        /** 기존 경기기록원 권한을 전체 제거 - 경기생성자는 삭제 대상에서 제외 */
        gameRecordAuthRepo.deleteRecordAuth( gameSeq );

        /** 화면에서 받은 대상을 경기 기록원으로 추가한다. */
        gameRecorders.stream()
                .filter(    i -> GameRecordAuthCode.RECORDER.getCode().equals( i.getGameRecordAuthCode() ) )
                .map(       i -> GameAuth.ofRecorder( gameSeq, i.getUserSeq() ) )
                .forEach(   gameRecordAuthRepo::saveGameAuth );
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
    public GameAuthDTO getGameAuthInfo( Long userSeq ) {
        List<GameAuth> userAuthList = gameRecordAuthRepo.findAuthList( userSeq );
        Map< Long, AuthLevel > authGames     = toGameAuthSet( userAuthList );
        return new GameAuthDTO( userSeq, authGames );
    }

    private Map< Long, AuthLevel > toGameAuthSet( List<GameAuth > authList ) {
        return authList.stream()
                .collect( Collectors.toMap(
                        GameAuth::getGameSeq,
                        item -> AuthLevel.of( AuthType.GAME_RECORD, Integer.parseInt( item.getGameRecordAuthCode() ) )
                    )
                );
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