package com.threeNerds.basketballDiary.mvc.repository;

import com.threeNerds.basketballDiary.mvc.domain.TeamJoinRequest;
import com.threeNerds.basketballDiary.mvc.dto.loginUser.userTeamManager.JoinRequestDTO;
import com.threeNerds.basketballDiary.mvc.dto.myTeam.CmnMyTeamDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeamJoinRequestRepository {

    int checkPendingJoinRequest(CmnMyTeamDTO invitationInfo);
    TeamJoinRequest checkJoinRequest(TeamJoinRequest invitationInfo); // TODO 임시용 차후 삭제예정

    //    TeamJoinRequest findUserByTeamJoinRequestSeq(JoinRequestDTO joinRequestDTO); TODO 2개 이상의  Controller에서 호출하는 조회메소드의 파라미터는  객체가 아닌 원시타입으로!!(그래야 유연하게 호출할 수 있음)
    TeamJoinRequest findUserByTeamJoinRequestSeq(Long teamJoinRequestSeq);

    int createJoinRequest(TeamJoinRequest teamJoinRequest);
    int updateJoinRequestState(TeamJoinRequest teamJoinRequest);

}
