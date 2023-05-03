package com.threeNerds.basketballDiary.mvc.game.repository.dto;

import com.threeNerds.basketballDiary.mvc.team.domain.Team;
import com.threeNerds.basketballDiary.mvc.game.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GameJoinManagerRepository {

    Team findGameCreatorTeam(Long gameSeq);

    /** 엔트리 조회 - 한팀의 엔트리 조회 */
    List<QuarterPlayerRecordDTO> findOneTeamEntry( SearchEntryDTO searchCond );

    List<GameOpponentDTO> findOpponents( SearchOppenentsDTO searchOppenentsDTO );

    List<GameJoinTeamInfoDTO> findGameJoinTeams( SearchGameJoinTeamDTO searchGameJoinTeamDTO );

    List<PlayerInfoDTO> findAllGameJoinPlayers( SearchPlayersDTO searchDTO );

    /** 경기에 참여한 팀원 조회 */
    List<GameJoinTeamMemberDTO> findAllGameJoinTeamMembers( SearchGameJoinTeamMemberDTO searchCond );
}
