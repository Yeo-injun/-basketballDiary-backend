package com.threeNerds.basketballDiary.mvc.team.repository;

import com.threeNerds.basketballDiary.mvc.team.domain.TeamRegularExercise;
import com.threeNerds.basketballDiary.mvc.team.dto.TeamRegularExerciseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeamRegularExerciseRepository {

    /**********
     * SELECT
     **********/
    List<TeamRegularExerciseDTO> findByTeamSeq(Long teamSeq);

    /**********
     * INSERT
     **********/
    int saveTeamRegularExercise(TeamRegularExercise teamRegularExercise);


    /**********
     * UPDATE
     **********/
    int updateTeamRegularExercise(TeamRegularExercise teamRegularExercise);

    /**********
     * DELETE
     **********/
    int deleteTeamRegularExercise(Long teamRegularExerciseSeq);
}
