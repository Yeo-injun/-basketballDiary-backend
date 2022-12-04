package com.threeNerds.basketballDiary.mvc.game.repository;

import com.threeNerds.basketballDiary.mvc.game.domain.QuarterPlayerRecords;
import com.threeNerds.basketballDiary.mvc.game.domain.QuarterTeamRecords;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QuarterTeamRecordsRepository {

    /** 쿼터목록 조회 - 게임참가팀Seq로 조회 */
    List<QuarterTeamRecords> findQuarterRecordsByJoinTeamSeq(Long gameJoinTeamSeq);

    QuarterTeamRecords find(QuarterTeamRecords quarterTeamRecords);

    void modify(QuarterTeamRecords quarterTeamRecords);

    Long create(QuarterTeamRecords quarterTeamRecords);
}
