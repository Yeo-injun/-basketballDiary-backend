package com.threeNerds.basketballDiary.mvc.game.domain.repository;

import com.threeNerds.basketballDiary.mvc.game.domain.GameAuth;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface GameRecordAuthRepository {

    /**********
     * SELECT
     **********/
    GameAuth findAuth(GameAuth gameRecorder);
    List<GameAuth> findAllAuthList( Long userSeq );
    List<GameAuth> findAllAuthByGameSeq( Long gameSeq );

    /**********
     * INSERT
     **********/
    /**
     * 경기기록 권한 생성
     */
    int saveGameAuth( GameAuth gameAuth );


    /**********
     * UPDATE
     **********/


    /**********
     * DELETE
     **********/
    /**
     * 게임 기록자 권한목록만 삭제
     */
    int deleteRecordAuth( Long gameSeq );

}

