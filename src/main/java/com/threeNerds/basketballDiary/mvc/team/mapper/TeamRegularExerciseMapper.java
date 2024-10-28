package com.threeNerds.basketballDiary.mvc.team.mapper;


import com.threeNerds.basketballDiary.mvc.team.mapper.dto.TeamRegularExerciseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeamRegularExerciseMapper {

    List<TeamRegularExerciseDTO> findAllExerciseByTeamSeq( Long teamSeq );

}
