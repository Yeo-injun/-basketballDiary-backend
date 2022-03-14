package com.threeNerds.basketballDiary.mvc.repository;

import com.threeNerds.basketballDiary.mvc.domain.TeamJoinRequest;
import com.threeNerds.basketballDiary.mvc.dto.loginUser.userTeamManager.JoinRequestDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeamJoinRequestRepository {

    TeamJoinRequest checkJoinRequest(TeamJoinRequest invitationInfo);
    TeamJoinRequest findUserByTeamJoinRequestSeq(JoinRequestDTO joinRequestDTO);

    int createJoinRequest(TeamJoinRequest teamJoinRequest);
    int updateJoinRequestState(TeamJoinRequest approvalInfo);

}
