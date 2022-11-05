package com.threeNerds.basketballDiary.mvc.game.repository.dto;

import com.threeNerds.basketballDiary.mvc.domain.Team;
import com.threeNerds.basketballDiary.mvc.game.domain.GameJoinTeam;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GameJoinManagerRepository {

    Team findGameCreatorTeam(Long gameSeq);
}
