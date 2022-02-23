package com.threeNerds.basketballDiary.mvc.repository;

import com.threeNerds.basketballDiary.mvc.domain.Team;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeamRepository {
    void saveTeam(Team team);
    void updateTeam(Team team);
    Team findByTeamSeq(Long teamSeq);
    List<Team> findAll();
    void deleteById(Long teamSeq);
}
