package com.threeNerds.basketballDiary.mvc.service;

import com.threeNerds.basketballDiary.constant.code.GameTypeCode;
import com.threeNerds.basketballDiary.exception.Error;
import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.mvc.domain.Game;
import com.threeNerds.basketballDiary.mvc.domain.TeamMember;
import com.threeNerds.basketballDiary.mvc.dto.game.GameCreationDTO;
import com.threeNerds.basketballDiary.mvc.repository.GameRepository;
import com.threeNerds.basketballDiary.mvc.repository.TeamMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class GameService {

    private final TeamMemberRepository teamMemberRepository;
    private final GameRepository gameRepository;

    public void DeleteGame(Long gameSeq){
        gameRepository.deleteGame(gameSeq);
    }

    /** 22.10.31
     * 게임 생성
     * @author 여인준
     **/
    public GameCreationDTO createGame(GameCreationDTO gc)
    {
        Game newGame = Game.create(gc);

        /** 게임유형 체크 */
        // 게임유형코드가 대회가 아니면
        boolean isCompetition = newGame.isCompetitionType();
        if (isCompetition) {
            // TODO 대회게임 생성 로직 구현 필요 - 차후...
            return gc;
        }

        /** 게임생성 요청 사용자 검증 - 게임을 생성하는 팀에 소속되어 있는지 확인 */
        Long creatorTeamMemberSeq = gc.getCreatorTeamMemberSeq();
        TeamMember teamMember = teamMemberRepository.findByTeamMemberSeq(creatorTeamMemberSeq);
        if (teamMember.isNotJoinTeam(gc.getTeamSeq())) {
            throw new CustomException(Error.ONLY_TEAM_MEMBER_HANDLE);
        }

        /** 게임 생성 */
        gameRepository.saveGame(newGame);
        gc.gameSeq(newGame.getGameSeq());
        return gc;
    }
}
