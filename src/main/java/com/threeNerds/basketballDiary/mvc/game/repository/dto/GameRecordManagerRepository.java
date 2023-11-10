package com.threeNerds.basketballDiary.mvc.game.repository.dto;

import com.threeNerds.basketballDiary.mvc.game.dto.*;
import com.threeNerds.basketballDiary.mvc.game.dto.getGameAllQuartersRecords.QuarterTeamRecordsDTO;
import com.threeNerds.basketballDiary.mvc.game.dto.getGameJoinPlayerRecordsByQuarter.response.PlayerQuarterRecordDTO;
import com.threeNerds.basketballDiary.mvc.game.dto.getGameQuarterRecords.response.TeamQuarterRecordsDTO;
import com.threeNerds.basketballDiary.mvc.game.dto.getGameRecorders.response.GameRecorderDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.GameCondDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.GameJoinTeamRecordDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.GameRecordDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GameRecordManagerRepository {

    /** 게임기록조회 */
    List<GameRecordDTO> findPagingGamesByTeamSeq( GameCondDTO gc );

    /** 게임참가팀들 조회 - 홈/어웨이팀 모두 */
    List<GameJoinTeamRecordDTO> findGameJoinTeamRecordsByGameSeq(Long gameSeq);

    /** 특정쿼터의 선수별 기록조회 (목록) */
    List<PlayerQuarterRecordDTO> findAllPlayerRecordsByQuarter(SearchGameDTO searchCond);

    /** 특정쿼터의 팀별 기록조회 (목록) */
    List<TeamQuarterRecordsDTO> findAllTeamsQuarterRecords(SearchGameDTO searchCond);

    /** 모든쿼터의 홈·어웨이 기록조회(목록) */
    List<QuarterTeamRecordsDTO> findAllQuarterRecords(SearchGameDTO searchGameDTO);

    /** 게임참가팀의 팀원조회 (이미 입력권한을 부여받은 선수는 제외한다) */
    List<PlayerInfoDTO> findTeamMembersByGameSeq(SearchGameDTO searchGameDTO);

    /** 경기기록 권한자 목록 조회 */
    List<GameRecorderDTO> findAllGameRecorders( SearchGameDTO  searchGameDTO );
}
