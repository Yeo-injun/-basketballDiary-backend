package com.threeNerds.basketballDiary.mvc.team.repository.dto;


import com.threeNerds.basketballDiary.mvc.team.dto.SearchTeamGameDTO;
import com.threeNerds.basketballDiary.mvc.team.dto.TeamGameDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeamGameRepository {

    /** 팀경기 목록 조회 ( 페이징 처리 ) */
    List<TeamGameDTO> findPagingTeamGamesByTeamSeq( SearchTeamGameDTO cond );
    /** 팀경기 전체 건수 조회 */
    int findTotalCountTeamGamesByTeamSeq( SearchTeamGameDTO cond );

}
