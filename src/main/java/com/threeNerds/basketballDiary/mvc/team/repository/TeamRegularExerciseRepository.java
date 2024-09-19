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
    @Deprecated
    List<TeamRegularExerciseDTO> findByTeamSeq(Long teamSeq);
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
