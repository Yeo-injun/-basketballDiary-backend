package com.threeNerds.basketballDiary.mvc.team.repository.dto;

import com.threeNerds.basketballDiary.mvc.team.dto.SearchTeamDTO;
import com.threeNerds.basketballDiary.mvc.team.dto.TeamDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeamInfoRepository {

    /**********
     * SELECT
     **********/
    List<TeamDTO> findPaginationTeamInfo( SearchTeamDTO searchTeamDTO );
    int findTotalCountTeamInfo( SearchTeamDTO searchTeamDTO );

}
