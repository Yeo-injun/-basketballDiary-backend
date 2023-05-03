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
    /**********
     * SELECT
     **********/
    Game findGame(Long gameSeq);
    // TODO findGame으로 대체 예정... Game레포지토리에서는 select 구문의 return은 무조건 해당 도메인클래스
    GameInfoDTO findGameBasicInfo(Long gameSeq);

    /**********
     * INSERT
     **********/
    /** 게임생성
     * - Game객체에 채번된 gameSeq가 할당됨.
     */
    int saveGame(Game newGame);

    /**********
     * UPDATE
     **********/
    /** 게임기록상태 Update
     * @parma Game (gameSeq, gameRecordStateCode)
     */
    int updateGameRecordState(Game game);

    /**********
     * DELETE
     **********/
    int deleteGame(Long gameSeq);
}
