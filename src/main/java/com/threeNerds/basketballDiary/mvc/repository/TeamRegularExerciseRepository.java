package com.threeNerds.basketballDiary.mvc.repository;

import com.threeNerds.basketballDiary.mvc.domain.TeamRegularExercise;
import com.threeNerds.basketballDiary.mvc.dto.team.team.SearchTeamDTO;
import com.threeNerds.basketballDiary.mvc.dto.team.team.TeamRegularExerciseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeamRegularExerciseRepository {
    List<TeamRegularExerciseDTO> findByTeamSeq(Long teamSeq);
    void saveTeamRegularExercise(TeamRegularExercise teamRegularExercise);
    void updateTeamRegularExercise(TeamRegularExercise teamRegularExercise);
    void deleteTeamRegularExercise(Long teamRegularExerciseSeq);
}
