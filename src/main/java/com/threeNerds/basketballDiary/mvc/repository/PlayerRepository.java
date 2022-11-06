package com.threeNerds.basketballDiary.mvc.repository;

import com.threeNerds.basketballDiary.mvc.dto.PlayerDTO;
import com.threeNerds.basketballDiary.mvc.dto.PlayerSearchDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.CmnMyTeamDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PlayerRepository {

    List<PlayerDTO> findPlayers(PlayerSearchDTO searchCond);
    List<PlayerDTO> findPlayers(CmnMyTeamDTO searchCond);


}
