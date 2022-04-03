package com.threeNerds.basketballDiary.mvc.repository;

import com.threeNerds.basketballDiary.mvc.domain.Team;
import com.threeNerds.basketballDiary.mvc.dto.team.team.SearchTeamDTO;
import com.threeNerds.basketballDiary.mvc.dto.team.team.TeamDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeamRepository {
    Long saveTeam(Team team);
    void updateTeam(Team team);
    Team findByTeamSeq(Long teamSeq);
    List<TeamDTO> findAll(SearchTeamDTO searchTeamDTO);
    List<TeamDTO> findPagingTeam(SearchTeamDTO searchTeamDTO);
    void deleteById(Long teamSeq);
}
