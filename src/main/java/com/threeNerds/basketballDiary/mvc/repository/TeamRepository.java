package com.threeNerds.basketballDiary.mvc.repository;

import com.threeNerds.basketballDiary.mvc.domain.Team;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TeamRepository {
    void saveTeam(Team team);
}
