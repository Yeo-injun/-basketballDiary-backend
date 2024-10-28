package com.threeNerds.basketballDiary.mvc.team.domain.repository;

import com.threeNerds.basketballDiary.mvc.team.domain.TeamRegularExercise;
import com.threeNerds.basketballDiary.mvc.team.mapper.dto.TeamRegularExerciseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeamRegularExerciseRepository {

    /**********
     * SELECT
     **********/
    List<TeamRegularExercise> find( TeamRegularExercise teamRegularExercise );

    /**********
     * INSERT
     **********/
    int saveTeamRegularExercise(TeamRegularExercise teamRegularExercise);


    /**********
     * UPDATE
     **********/

    /**********
     * DELETE
     **********/
    int deleteAllByTeamSeq( Long teamSeq );

}
