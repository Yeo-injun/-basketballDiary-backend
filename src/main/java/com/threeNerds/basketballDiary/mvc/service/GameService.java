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

import java.util.Optional;

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
        /** 게임생성 요청 사용자 검증 - 게임을 생성하는 팀에 소속되어 있는지 확인 */
        TeamMember tmParam = TeamMember.builder()
                .userSeq(gc.getUserSeq())
                .teamSeq(gc.getTeamSeq())
                .build();

        TeamMember tmResult = Optional.ofNullable(teamMemberRepository.findTeamMemberByUserAndTeamSeq(tmParam))
                .orElseThrow(()-> new CustomException(Error.ONLY_TEAM_MEMBER_HANDLE));

        /** 게임 생성 */
        gc.creatorTeamMemberSeq(tmResult.getTeamMemberSeq());
        Game newGame = Game.createDefault(gc);
        gameRepository.saveGame(newGame);

        /** 생성된 게임Seq 반환 */
        gc.gameSeq(newGame.getGameSeq());
        return gc;
    }
}
