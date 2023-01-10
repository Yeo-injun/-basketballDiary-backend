package com.threeNerds.basketballDiary.mvc.game.repository;

import com.threeNerds.basketballDiary.mvc.game.domain.Game;
import com.threeNerds.basketballDiary.mvc.game.dto.GameInfoDTO;
import com.threeNerds.basketballDiary.mvc.game.dto.PlayerInfoDTO;
import com.threeNerds.basketballDiary.mvc.game.dto.SearchMatchPlayersDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.GameCondDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.GameRecordDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface GameRepository {

    void deleteGame(Long gameSeq);

    /** 게임생성
     * - Game객체에 채번된 gameSeq가 할당됨.
     * @return Long insert된 데이터의 갯수 할당.
     */
    Long saveGame(Game newGame);

    GameInfoDTO getGameInfo(Long gameSeq);

    /** 게임기록상태 Update
     * @parma Game (gameSeq, gameRecordStateCode)
     * @return Long insert된 데이터의 갯수 할당.
     */
    Integer updateGameRecordState(Game game);
}
