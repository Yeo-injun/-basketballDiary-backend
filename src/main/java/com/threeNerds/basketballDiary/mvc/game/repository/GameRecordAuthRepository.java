package com.threeNerds.basketballDiary.mvc.game.repository;

import com.threeNerds.basketballDiary.mvc.game.controller.dto.GameAuthDTO;
import com.threeNerds.basketballDiary.mvc.game.controller.dto.GameAuthRecorderDTO;
import com.threeNerds.basketballDiary.mvc.game.controller.response.GameAuthRecordersResponse;
import com.threeNerds.basketballDiary.mvc.game.domain.Game;
import com.threeNerds.basketballDiary.mvc.game.domain.GameRecordAuth;
import com.threeNerds.basketballDiary.mvc.game.dto.GameInfoDTO;
import com.threeNerds.basketballDiary.mvc.game.dto.PlayerInfoDTO;
import com.threeNerds.basketballDiary.mvc.game.dto.SearchMatchPlayersDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GameRecordAuthRepository {

    /** 게임기록권한생성
     */
    Long saveGameRecordAuth(GameRecordAuth gameRecordAuth);

    /**
     * 게임기록자 조회
     */
    List<GameAuthRecordersResponse> searchGameRecorders(GameAuthDTO gameAuthDTO);

}
