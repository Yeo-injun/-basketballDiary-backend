package com.threeNerds.basketballDiary.mvc.game.repository;

import com.threeNerds.basketballDiary.mvc.game.domain.GameAuth;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface GameRecordAuthRepository {

    /**********
     * SELECT
     **********/
    List<GameAuth> findAuthList( Long userSeq );

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

