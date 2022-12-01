package com.threeNerds.basketballDiary.mvc.game.service;

import com.threeNerds.basketballDiary.exception.Error;
import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.mvc.game.domain.Game;
import com.threeNerds.basketballDiary.mvc.game.dto.*;
import com.threeNerds.basketballDiary.mvc.myTeam.domain.TeamMember;
import com.threeNerds.basketballDiary.mvc.game.repository.GameRepository;
import com.threeNerds.basketballDiary.mvc.myTeam.repository.TeamMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
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

        /** 게임기록권한 정보 생성 - TODO 게임 생성한 사람 입력*/

        /** 생성된 게임Seq 반환 */
        gc.gameSeq(newGame.getGameSeq());
        return gc;
    }

    /**
     * 22.11.12
     * 게임기록 상세조회
     * @author 이성주
     */
    public GameInfoDTO getGameInfo(Long gameSeq){
        return Optional.ofNullable(gameRepository.getGameInfo(gameSeq))
                .orElseThrow(()->new CustomException(Error.NOT_FOUND_GAME));
    }

    /**
     * 22.11.30
     * 경기참가선수 조회
     * @author 이성주
     */
    public List<MatchPlayersInfoDTO> getMatchPlayersInfo(Long gameSeq,String homeAwayCode){
        // homeAwayCode가 없을때는 홈,어웨이 모두 조회
        // homeAwayCode가 있을때는 해당하는 팀의 팀원 조회

        // 쿼리는 homeAwayCode에 따라 동적으로 작성
        // response 객체에 담는 gameSeq, homeAwayCode, teamSeq 는 어떻게 설정해줘야 되는지
        // response 객체에 단일 데이터와 리스트형식의 데이터를 함께 보내야된다.
        SearchMatchPlayersDTO matchPlayersDTO = new SearchMatchPlayersDTO()
                                                    .gameSeq(gameSeq)
                                                    .homeAwayCode(homeAwayCode);

        List<PlayerInfoDTO> matchPlayers = gameRepository.getMatchPlayers(matchPlayersDTO);

        Long retGameSeq = matchPlayers.get(0).getGameSeq();
        Long retTeamSeq = matchPlayers.get(0).getTeamSeq();

//        MatchPlayersInfoDTO matchPlayersInfoDTO = new MatchPlayersInfoDTO()
//                .gameSeq()
        // MatchPlayerInfoDTO 에 대입
       return null;
    }
}
