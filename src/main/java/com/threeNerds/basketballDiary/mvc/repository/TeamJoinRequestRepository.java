package com.threeNerds.basketballDiary.mvc.repository;

import com.threeNerds.basketballDiary.mvc.domain.TeamJoinRequest;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TeamJoinRequestRepository {

    int checkPendingJoinRequest(TeamJoinRequest invitationInfo);

    //    TeamJoinRequest findUserByTeamJoinRequestSeq(JoinRequestDTO joinRequestDTO); TODO 2개 이상의  Controller에서 호출하는 조회메소드의 파라미터는  객체가 아닌 원시타입으로!!(그래야 유연하게 호출할 수 있음)
    TeamJoinRequest findUserByTeamJoinRequestSeq(Long teamJoinRequestSeq);

    int createJoinRequest(TeamJoinRequest teamJoinRequest);
    int updateJoinRequestState(TeamJoinRequest teamJoinRequest);

}
