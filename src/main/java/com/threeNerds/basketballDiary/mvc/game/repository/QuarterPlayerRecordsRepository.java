package com.threeNerds.basketballDiary.mvc.game.repository;

import com.threeNerds.basketballDiary.mvc.game.domain.QuarterPlayerRecords;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QuarterPlayerRecordsRepository {
    /**********
     * SELECT
     **********/
    List<QuarterPlayerRecords> findAllInGame(Long gameSeq);

    /**********
     * INSERT
     **********/
    int save(QuarterPlayerRecords quarterPlayerRecords);

    /**********
     * UPDATE
     **********/
    int updateInGameYn( QuarterPlayerRecords params );
    int updateInGameYnForAllQuarterPlayer( QuarterPlayerRecords params );

    int updateQuarterRecords(QuarterPlayerRecords quarterPlayerRecords);

    /**********
     * DELETE
     **********/
    Long deleteGameQuarter(QuarterPlayerRecords quarterPlayerRecords);

}
