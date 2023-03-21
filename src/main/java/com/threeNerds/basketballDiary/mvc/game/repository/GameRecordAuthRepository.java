package com.threeNerds.basketballDiary.mvc.game.repository;

import com.threeNerds.basketballDiary.mvc.game.controller.dto.GameAuthDTO;
import com.threeNerds.basketballDiary.mvc.game.controller.response.GameAuthRecordersResponse;
import com.threeNerds.basketballDiary.mvc.game.domain.GameRecordAuth;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GameRecordAuthRepository {

    /**********
     * SELECT
     **********/
    /**
     * 게임기록자 조회
     */
    List<GameAuthRecordersResponse> searchGameRecorders(GameAuthDTO gameAuthDTO);

    /**********
     * INSERT
     **********/
    /**
     * 게임기록권한생성
     */
    int saveGameRecordAuth(GameRecordAuth gameRecordAuth);

    /**********
     * UPDATE
     **********/


    /**********
     * DELETE
     **********/

}

