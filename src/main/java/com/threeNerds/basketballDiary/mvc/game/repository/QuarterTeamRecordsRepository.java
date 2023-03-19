package com.threeNerds.basketballDiary.mvc.game.repository;

import com.threeNerds.basketballDiary.mvc.game.domain.QuarterPlayerRecords;
import com.threeNerds.basketballDiary.mvc.game.domain.QuarterTeamRecords;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QuarterTeamRecordsRepository {

    List<QuarterTeamRecords> findAllGameJoinTeam(QuarterTeamRecords inqCond);
    /** 쿼터목록 조회 - 게임참가팀Seq로 조회 TODO 메소드명 고치기 - findByGameJoinTeamSeq */
    List<QuarterTeamRecords> findQuarterRecordsByJoinTeamSeq(Long gameJoinTeamSeq);

    int save(QuarterTeamRecords quarterTeamRecords);

    int updateQuarterRecords(QuarterTeamRecords teamRecords);

    int deleteGameQuarter(QuarterTeamRecords quarterTeamRecords);
}
