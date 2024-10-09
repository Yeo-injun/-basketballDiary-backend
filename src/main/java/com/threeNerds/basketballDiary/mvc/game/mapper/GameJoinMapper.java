package com.threeNerds.basketballDiary.mvc.game.mapper;

import com.threeNerds.basketballDiary.mvc.game.mapper.dto.*;
import com.threeNerds.basketballDiary.mvc.team.domain.Team;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GameJoinMapper {

    Team findGameCreatorTeam(Long gameSeq);

    /** 엔트리 조회 - 한팀의 엔트리 조회 */
    List<QuarterPlayerRecordDTO> findOneTeamEntry(SearchEntryDTO searchCond );

    /** 상대팀 목록 조회 - 페이징 처리 */
    List<GameOpponentDTO> findPaginationOpponents(SearchOppenentsDTO searchOppenentsDTO );
    int findTotalCountOpponents( SearchOppenentsDTO searchOppenentsDTO );
    List<GameOpponentDTO> findOpponents( SearchOppenentsDTO searchOppenentsDTO );

    List<GameJoinTeamInfoDTO> findGameJoinTeams(SearchGameJoinTeamDTO searchGameJoinTeamDTO );

    List<PlayerInfoDTO> findAllGameJoinPlayers(Long gameSeq );

    List<PlayerInfoDTO> findPaginationGameJoinPlayers( SearchPlayersDTO searchDTO );
    List<PlayerInfoDTO> findGameJoinPlayers( SearchPlayersDTO searchDTO );
    int findTotalCountGameJoinPlayers( SearchPlayersDTO searchDTO );
}
