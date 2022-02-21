package com.threeNerds.basketballDiary.mvc.repository;

import com.threeNerds.basketballDiary.mvc.domain.TeamRegularExercise;

import java.util.List;

public interface TeamRegularExerciseRepository {
    TeamRegularExercise findByTeamRegularExerciseSeq(Long teamRegularExerciseSeq);
    List<TeamRegularExercise> findByTeamSeq(Long teamSeq);
    // List<TeamRegularExercise> findAll();
}
