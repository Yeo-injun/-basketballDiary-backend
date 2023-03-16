package com.threeNerds.basketballDiary.mvc.game.repository.dto;

import com.threeNerds.basketballDiary.mvc.team.domain.Team;
import com.threeNerds.basketballDiary.mvc.game.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GameJoinManagerRepository {

    Team findGameCreatorTeam(Long gameSeq);
    List<QuarterPlayerRecordDTO> findEntryList(SearchEntryDTO searchEntryDTO);

    List<GameOpponentDTO> findOpponents(SearchOppenentsDTO searchOppenentsDTO);

    List<GameJoinTeamInfoDTO> findGameJoinTeams(SearchGameJoinTeamDTO searchGameJoinTeamDTO);

    List<PlayerInfoDTO> findGameJoinPlayers(SearchPlayersDTO searchDTO);
}
