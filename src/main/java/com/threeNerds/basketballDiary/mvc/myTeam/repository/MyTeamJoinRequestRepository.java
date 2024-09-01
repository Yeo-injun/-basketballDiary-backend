package com.threeNerds.basketballDiary.mvc.myTeam.repository;

import com.threeNerds.basketballDiary.mvc.myTeam.dto.TeamJoinRequestDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MyTeamJoinRequestRepository {
    /**********
     * SELECT
     **********/
    // 소속팀이 조회할 수 있는 모든 팀가입요청 목록
    List<TeamJoinRequestDTO> findAllByMyTeam( TeamJoinRequestDTO param );
}
