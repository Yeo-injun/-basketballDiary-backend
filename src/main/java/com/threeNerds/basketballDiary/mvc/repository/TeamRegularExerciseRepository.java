package com.threeNerds.basketballDiary.mvc.repository;

import com.threeNerds.basketballDiary.mvc.domain.TeamRegularExercise;
import com.threeNerds.basketballDiary.mvc.dto.team.team.SearchTeamDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeamRegularExerciseRepository {
    TeamRegularExercise findByTeamRegularExerciseSeq(Long teamRegularExerciseSeq);
    TeamRegularExercise findBySearchTeamDTO(SearchTeamDTO searchTeamDTO);
    List<TeamRegularExercise> findByTeamSeq(Long teamSeq);
    void saveTeamRegularExercise(TeamRegularExercise teamRegularExercise);
    void updateTeamRegularExercise(TeamRegularExercise teamRegularExercise);
    void deleteTeamRegularExercise(Long teamRegularExerciseSeq);
}
