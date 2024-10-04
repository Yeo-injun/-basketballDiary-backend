package com.threeNerds.basketballDiary.mvc.team.repository;

import com.threeNerds.basketballDiary.mvc.team.domain.Team;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface TeamRepository {

    /**********
     * SELECT
     **********/
    Team findByTeamSeq(Long teamSeq);

    /**********
     * INSERT
     **********/
    int saveTeam(Team team);

    /**********
     * UPDATE
     **********/
    int updateTeam(Team team);

    /**********
     * DELETE
     **********/
    int deleteById(Long teamSeq);
}
