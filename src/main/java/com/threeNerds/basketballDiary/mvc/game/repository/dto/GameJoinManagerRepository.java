package com.threeNerds.basketballDiary.mvc.game.repository.dto;

import com.threeNerds.basketballDiary.mvc.team.domain.Team;
import com.threeNerds.basketballDiary.mvc.team.dto.TeamDTO;
import com.threeNerds.basketballDiary.mvc.game.dto.*;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.FindGameHomeAwayDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GameJoinManagerRepository {

    Team findGameCreatorTeam(Long gameSeq);
    List<QuarterPlayerRecordDTO> findEntryList(SearchEntryDTO searchEntryDTO);

    List<TeamDTO> searchOpponents(SearchOppenentsDTO searchOppenentsDTO);

    List<FindGameHomeAwayDTO> findGameTeams(SearchGameHomeAwayDTO searchGameHomeAwayDTO);

    List<PlayerInfoDTO> findGameJoinPlayers(SearchPlayersDTO searchDTO);
}
