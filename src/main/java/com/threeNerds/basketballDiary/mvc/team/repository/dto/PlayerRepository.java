package com.threeNerds.basketballDiary.mvc.team.repository.dto;

import com.threeNerds.basketballDiary.mvc.team.dto.PlayerDTO;
import com.threeNerds.basketballDiary.mvc.team.dto.PlayerSearchDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.CmnMyTeamDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PlayerRepository {

    List<PlayerDTO> findPlayers(PlayerSearchDTO searchCond);
    List<PlayerDTO> findPlayers(CmnMyTeamDTO searchCond);


}
