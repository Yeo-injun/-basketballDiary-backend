package com.threeNerds.basketballDiary.mvc.game.repository;

import com.threeNerds.basketballDiary.mvc.game.domain.QuarterPlayerRecords;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QuarterPlayerRecordsRepository {

    QuarterPlayerRecords find(QuarterPlayerRecords quarterPlayerRecords);
    List<QuarterPlayerRecords> findAllInGame(Long gameSeq);


    int modify(QuarterPlayerRecords quarterPlayerRecords);
    int modifyInGameYn(QuarterPlayerRecords modParamForInGameYn);

    Long create(QuarterPlayerRecords quarterPlayerRecords);

}
