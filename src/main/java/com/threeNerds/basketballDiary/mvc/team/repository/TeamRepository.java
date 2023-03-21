package com.threeNerds.basketballDiary.mvc.team.repository;

import com.threeNerds.basketballDiary.mvc.team.domain.Team;
import com.threeNerds.basketballDiary.mvc.team.dto.SearchTeamDTO;
import com.threeNerds.basketballDiary.mvc.team.dto.TeamDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeamRepository {

    /**********
     * SELECT
     **********/
    Team findByTeamSeq(Long teamSeq);
    List<TeamDTO> findPagingTeam(SearchTeamDTO searchTeamDTO);

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
