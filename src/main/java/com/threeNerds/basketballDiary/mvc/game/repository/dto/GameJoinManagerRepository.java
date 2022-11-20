package com.threeNerds.basketballDiary.mvc.game.repository.dto;

import com.threeNerds.basketballDiary.mvc.domain.Team;
import com.threeNerds.basketballDiary.mvc.dto.team.team.TeamDTO;
import com.threeNerds.basketballDiary.mvc.game.domain.GameJoinTeam;
import com.threeNerds.basketballDiary.mvc.game.dto.SearchGameHomeAwayDTO;
import com.threeNerds.basketballDiary.mvc.game.dto.SearchOppenentsDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.FindGameHomeAwayDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GameJoinManagerRepository {

    Team findGameCreatorTeam(Long gameSeq);

    List<TeamDTO> searchOpponents(SearchOppenentsDTO searchOppenentsDTO);

    List<FindGameHomeAwayDTO> findGameTeams(SearchGameHomeAwayDTO searchGameHomeAwayDTO);
}
