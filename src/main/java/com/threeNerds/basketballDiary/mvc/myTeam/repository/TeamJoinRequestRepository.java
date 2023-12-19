package com.threeNerds.basketballDiary.mvc.myTeam.repository;

import com.threeNerds.basketballDiary.mvc.myTeam.domain.TeamJoinRequest;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TeamJoinRequestRepository {
    /**********
     * SELECT
     **********/
    TeamJoinRequest findUserByTeamJoinRequestSeq(Long teamJoinRequestSeq);
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
