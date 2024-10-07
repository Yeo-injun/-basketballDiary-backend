package com.threeNerds.basketballDiary.mvc.team.mapper;

import com.threeNerds.basketballDiary.mvc.team.mapper.dto.SearchTeamDTO;
import com.threeNerds.basketballDiary.mvc.team.mapper.dto.TeamDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeamMapper {

    /**********
     * SELECT
     **********/
    List<TeamDTO> findPaginationTeamInfo( SearchTeamDTO searchTeamDTO );
    int findTotalCountTeamInfo( SearchTeamDTO searchTeamDTO );

}
