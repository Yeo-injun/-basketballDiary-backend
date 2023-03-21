package com.threeNerds.basketballDiary.mvc.game.service;

import com.threeNerds.basketballDiary.constant.code.GameRecordStateCode;
import com.threeNerds.basketballDiary.exception.Error;
import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.mvc.game.domain.Game;
import com.threeNerds.basketballDiary.mvc.game.domain.GameRecordAuth;
import com.threeNerds.basketballDiary.mvc.game.dto.*;
import com.threeNerds.basketballDiary.mvc.game.repository.GameRecordAuthRepository;
import com.threeNerds.basketballDiary.mvc.myTeam.domain.TeamMember;
import com.threeNerds.basketballDiary.mvc.game.repository.GameRepository;
import com.threeNerds.basketballDiary.mvc.myTeam.repository.TeamMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class GameService {

    private final TeamMemberRepository teamMemberRepository;
    private final GameRepository gameRepository;
    private final GameRecordAuthRepository gameRecordAuthRepo;

    public void deleteGame(Long gameSeq){
        boolean isDeleteGame = gameRepository.deleteGame(gameSeq) > 0;
        if ( !isDeleteGame ) {
            // TODO 임시 에러 던지기 - 삭제할 게임이 없습니다.
            throw new CustomException(Error.INVALID_PARAMETER);
        }
        // TODO 게임참가팀, 게임참가선수, 쿼터기록, 게임기록권한 테이블도 다 삭제해줘야 함....
    }

    /** 22.10.31
     * 게임 생성
     * @author 여인준
     **/
    public GameCreationDTO createGame(GameCreationDTO gc)
    {
        /** 게임생성 요청 사용자 검증 - 게임을 생성하는 팀에 소속되어 있는지 확인 */
        TeamMember tmParam = TeamMember.builder()
                .userSeq(gc.getUserSeq())
                .teamSeq(gc.getTeamSeq())
                .build();

        TeamMember tm = Optional.ofNullable(teamMemberRepository.findTeamMemberByUserAndTeamSeq(tmParam))
                .orElseThrow(()-> new CustomException(Error.ONLY_TEAM_MEMBER_HANDLE));
        Long teamMemeberSeq = tm.getTeamMemberSeq();

        /** 게임 생성 */
        gc.creatorTeamMemberSeq(teamMemeberSeq);
        Game newGame = Game.createDefault(gc);
        gameRepository.saveGame(newGame);
        Long newGameSeq = newGame.getGameSeq();

        /** 게임기록권한 정보 생성 */
        GameRecordAuth gameCreatorAuth = GameRecordAuth.getCreatorAuth(newGameSeq, teamMemeberSeq);
        gameRecordAuthRepo.saveGameRecordAuth(gameCreatorAuth);

        /** 생성된 게임Seq 반환 */
        gc.gameSeq(newGameSeq);
        return gc;
    }

    /**
     * 22.11.12
     * 게임기록 상세조회
     * @author 이성주
     */
    public GameInfoDTO getGameInfo(Long gameSeq)
    {
        return Optional
                .ofNullable(gameRepository.findGameBasicInfo(gameSeq))
                .orElseThrow(()->new CustomException(Error.NOT_FOUND_GAME));
    }

    /**
     * 22.12.12
     * 게임 확정
     * @author 이성주
     */
    public void confirmGame(Long gameSeq)
    {
        /** 게임기록상태코드 변경 - >> 게임확정(03) */
        Game gameConfirm = Game.builder()
                .gameSeq(gameSeq)
                .gameRecordStateCode(GameRecordStateCode.CONFIRMATION.getCode())
                .build();
            gameRepository.updateGameRecordState(gameConfirm);
    }
}
