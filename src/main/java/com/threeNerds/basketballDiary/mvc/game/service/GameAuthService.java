package com.threeNerds.basketballDiary.mvc.game.service;

import com.threeNerds.basketballDiary.constant.code.type.GameRecordAuthCode;
import com.threeNerds.basketballDiary.mvc.game.domain.*;
import com.threeNerds.basketballDiary.mvc.game.dto.SearchGameDTO;
import com.threeNerds.basketballDiary.mvc.game.dto.getGameRecorders.request.GetGameRecordersRequest;
import com.threeNerds.basketballDiary.mvc.game.dto.getGameRecorders.response.GameRecorderDTO;
import com.threeNerds.basketballDiary.mvc.game.dto.getGameRecorders.response.GetGameRecordersResponse;
import com.threeNerds.basketballDiary.mvc.game.dto.saveGameRecorder.request.SaveGameRecordersRequest;
import com.threeNerds.basketballDiary.mvc.game.repository.*;
import com.threeNerds.basketballDiary.mvc.game.repository.dto.GameRecordManagerRepository;
import com.threeNerds.basketballDiary.mvc.game.service.dto.GameAuthDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class GameAuthService {

    private final GameRecordManagerRepository gameRecordManagerRepo;
    private final GameRepository gameRepository;
    private final GameRecordAuthRepository gameRecordAuthRepo;

 /**
     * 2022.01.04
     * 경기 관리자 조회 ( 경기 생성자, 경기 기록자 등 )
     * @author 이성주
     */
    public GetGameRecordersResponse getGameManagers( GetGameRecordersRequest request ) {
        // TODO 경기기록 권한자의 요건 최종 검토
        // 1. 게임에 참가하는 팀에 소속되어 있어야 한다.
        // 2, 게임에 참가선수로 등록되지 않아도 된다.
        // 이에따른 조회 쿼리 및 조회 내용 정립
        Long gameSeq = request.getGameSeq();
        SearchGameDTO gameCond = new SearchGameDTO().gameSeq( gameSeq );
        List<GameRecorderDTO> gameRecorders = gameRecordManagerRepo.findAllGameRecorders( gameCond );

        // TODO 자체전일 경우 TeamName에 prifix 붙여주기 ( HOME_ or AWAY_ )
        return new GetGameRecordersResponse( gameRecorders );
    }

    /**
     * 2022.01.14
     * 경기기록 권한 일괄 부여
     * @author 이성주
     */
    public void saveGameRecorders( SaveGameRecordersRequest request ) {
        Long gameSeq                        = request.getGameSeq();
        List<GameRecorderDTO> gameRecorders = request.getGameRecorders();

        /** 기존 경기 권한을 제거하고, 새롭게 생성한다. - 경기생성자는 삭제대상에서 제외 */
        gameRecordAuthRepo.deleteRecordAuth( gameSeq );

        /** 화면에서 받은 대상을 게임기록자로 추가한다. */
        for ( GameRecorderDTO gameRecorder : gameRecorders ) {
            // 게임생성자를 제외하고 insert문을 실행한다.
            if ( GameRecordAuthCode.CREATOR.getCode().equals( gameRecorder.getGameRecordAuthCode() ) ) {
                continue;
            }

            /** TODO 기록자 중복 체크 - GameSeq와 TeamMemberSeq로 조회시 기존 데이터 존재하는지 체크 */

            GameAuth recordAuth = GameAuth.createRecordAuth( gameSeq, gameRecorder.getUserSeq() );
            gameRecordAuthRepo.saveGameAuth( recordAuth );
        }
    }


    /**
     * 2024.02.24
     * 사용자의 경기권한 정보 조회
     * - 사용자가 가지고 있는 경기권한을 권한 수준 별로 조회한다.
     * cf. 로그인시 사용자의 경기권한을 수준별로 조회해서 Session에 보관한다.
     * @author 여인준
     */
    public GameAuthDTO getGameAuthInfo( Long userSeq ) {

        List<GameAuth> userAuthList = gameRecordAuthRepo.findAuthList( userSeq );

        Set<String> creatorAuth     = toGameAuthSet( userAuthList, GameRecordAuthCode.CREATOR );
        Set<String> recorderAuth    = toGameAuthSet( userAuthList, GameRecordAuthCode.RECORDER );

        return new GameAuthDTO( userSeq, creatorAuth, recorderAuth );
    }

    private Set<String> toGameAuthSet( List<GameAuth > authList, GameRecordAuthCode authCode ) {
        return authList.stream()
                .map( GameAuth::getGameRecordAuthCode )
                .filter( code -> authCode.getCode().equals( code ) )
                .collect( Collectors.toSet() );
    }

}