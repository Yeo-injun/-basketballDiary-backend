package com.threeNerds.basketballDiary.mvc.team.repository;

import com.threeNerds.basketballDiary.mvc.team.domain.TeamRegularExercise;
import com.threeNerds.basketballDiary.mvc.team.dto.TeamRegularExerciseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeamRegularExerciseRepository {
    List<TeamRegularExerciseDTO> findByTeamSeq(Long teamSeq);
    void saveTeamRegularExercise(TeamRegularExercise teamRegularExercise);
    void updateTeamRegularExercise(TeamRegularExercise teamRegularExercise);
    void deleteTeamRegularExercise(Long teamRegularExerciseSeq);
}
