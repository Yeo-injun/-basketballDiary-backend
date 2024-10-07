package com.threeNerds.basketballDiary.mvc.team.mapper;


import com.threeNerds.basketballDiary.mvc.team.mapper.dto.SearchTeamGameDTO;
import com.threeNerds.basketballDiary.mvc.team.mapper.dto.TeamGameDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeamGameMapper {

    /** 팀경기 목록 조회 ( 페이징 처리 ) */
    List<TeamGameDTO> findPagingTeamGamesByTeamSeq( SearchTeamGameDTO cond );
    /** 팀경기 전체 건수 조회 */
    int findTotalCountTeamGamesByTeamSeq( SearchTeamGameDTO cond );

}
