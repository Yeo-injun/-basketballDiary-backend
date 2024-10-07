package com.threeNerds.basketballDiary.mvc.game.mapper;

import com.threeNerds.basketballDiary.mvc.game.mapper.dto.QuarterTeamRecordsDTO;
import com.threeNerds.basketballDiary.mvc.game.mapper.dto.PlayerQuarterRecordDTO;
import com.threeNerds.basketballDiary.mvc.game.mapper.dto.SearchGameDTO;
import com.threeNerds.basketballDiary.mvc.game.mapper.dto.TeamQuarterRecordsDTO;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GameRecordMapper {

    /** 특정쿼터의 선수별 기록조회 (목록) */
    List<PlayerQuarterRecordDTO> findAllPlayerRecordsByQuarter(SearchGameDTO searchCond);

    /** 특정쿼터의 팀별 기록조회 (목록) */
    List<TeamQuarterRecordsDTO> findAllTeamsQuarterRecords(SearchGameDTO searchCond);

    /** 모든쿼터의 홈·어웨이 기록조회(목록) */
    List<QuarterTeamRecordsDTO> findAllQuarterRecords( Long gameSeq );

}
