package com.threeNerds.basketballDiary.mvc.team.domain.repository;

import com.threeNerds.basketballDiary.mvc.team.domain.TeamJoinRequest;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TeamJoinRequestRepository {
    /**********
     * SELECT
     **********/
    TeamJoinRequest findBySeq( Long teamJoinRequestSeq );
    int checkPendingJoinRequest(TeamJoinRequest invitationInfo);

    /**********
     * INSERT
     **********/
    int createJoinRequest(TeamJoinRequest teamJoinRequest);

    /**********
     * UPDATE
     **********/
    int updateJoinRequestState(TeamJoinRequest teamJoinRequest);

}
