package com.threeNerds.basketballDiary.mvc.game.repository;

import com.threeNerds.basketballDiary.mvc.game.domain.QuarterPlayerRecords;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuarterPlayerRecordsRepository {

    QuarterPlayerRecords find(QuarterPlayerRecords quarterPlayerRecords);

    void modify(QuarterPlayerRecords quarterPlayerRecords);
}
