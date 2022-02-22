package com.threeNerds.basketballDiary.mvc.repository;

import com.threeNerds.basketballDiary.mvc.domain.TeamRegularExercise;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeamRegularExerciseRepository {
    TeamRegularExercise findByTeamRegularExerciseSeq(Long teamRegularExerciseSeq);
    List<TeamRegularExercise> findByTeamSeq(Long teamSeq);
    // List<TeamRegularExercise> findAll();
}
