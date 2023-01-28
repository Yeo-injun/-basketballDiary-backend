package com.threeNerds.basketballDiary.mvc.game.repository.dto;

import com.threeNerds.basketballDiary.mvc.game.dto.*;
import com.threeNerds.basketballDiary.mvc.game.dto.response.getGameAllQuartersRecords.QuarterTeamRecordsDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.GameCondDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.GameJoinTeamRecordDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.GameRecordDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.QuarterRecordDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GameRecordManagerRepository {

    /** 게임기록조회
     * @param gc
     * @return
     */
    List<GameRecordDTO> findGamesByTeamSeq(GameCondDTO gc);

    /** 게임참가팀들 조회 - 홈/어웨이팀 모두 */
    List<GameJoinTeamRecordDTO> findGameJoinTeamRecordsByGameSeq(Long gameSeq);

    /** 게임참가팀의 쿼터기록 조회 - 쿼터기록 */
    List<QuarterRecordDTO> findJoinTeamQuarterRecords(Long gameJoinTeamSeq);


    /** 특정쿼터의 선수별 기록조회(단건)
     * @param searchGameDTO 게임조회용 DTO
     */
    PlayerRecordDTO findPlayerRecordsByQuarter(SearchGameDTO searchGameDTO);

    /** 특정쿼터의 선수별 기록조회(목록)
     * @param searchGameDTO 게임조회용 DTO
     */
    List<PlayerRecordDTO> findAllPlayerRecordsByQuarter(SearchGameDTO searchGameDTO);

    /**
     * 특정쿼터의 홈·어웨이 기록조회(단건)
     * @param searchGameDTO 게임조회용 DTO
     */
    HomeAwayTeamRecordDTO findHomeAwayTeamRecordsByQuarter(SearchGameDTO searchGameDTO);

    /**
     * 모든쿼터의 홈·어웨이 기록조회(목록)
     * @param searchGameDTO 게임조회용 DTO
     * @return
     */
//    List<HomeAwayTeamRecordDTO> findAllHomeAwayTeamRecordsByQuarter(SearchGameDTO searchGameDTO);
    List<QuarterTeamRecordsDTO> findAllQuarterRecords(SearchGameDTO searchGameDTO);

    /**
     * 쿼터 삭제(QUARTER_PLAYER_RECORDS TABLE)
     * @param quarterCodeDTO
     */
    void deleteQuarterPlayerRecords(QuarterCodeDTO quarterCodeDTO);

    /**
     * 쿼터 삭제(QUARTER_TEAM_RECORDS TABLE)
     * @param quarterCodeDTO
     */
    void deleteQuarterTeamRecords(QuarterCodeDTO quarterCodeDTO);

    /** 게임참가팀의 팀원조회 (이미 입력권한을 부여받은 선수는 제외한다)
     * @param searchGameDTO 게임조회용 DTO
     */
    List<PlayerInfoDTO> findTeamMembersByGameSeq(SearchGameDTO searchGameDTO);
}
