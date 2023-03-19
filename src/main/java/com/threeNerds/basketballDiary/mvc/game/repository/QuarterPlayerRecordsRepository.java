package com.threeNerds.basketballDiary.mvc.game.repository;

import com.threeNerds.basketballDiary.mvc.game.domain.QuarterPlayerRecords;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QuarterPlayerRecordsRepository {

    List<QuarterPlayerRecords> findAllInGame(Long gameSeq);

    int save(QuarterPlayerRecords quarterPlayerRecords);

    int updateInGameYn(QuarterPlayerRecords modParamForInGameYn);
    int updateQuarterRecords(QuarterPlayerRecords quarterPlayerRecords);

    Long deleteGameQuarter(QuarterPlayerRecords quarterPlayerRecords);

}
