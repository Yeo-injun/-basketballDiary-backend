package com.threeNerds.basketballDiary.mvc.repository;

import com.threeNerds.basketballDiary.mvc.domain.TeamJoinRequest;
import com.threeNerds.basketballDiary.mvc.dto.JoinRequestDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeamJoinRequestRepository {

    JoinRequestDTO findUserByTeamJoinRequestSeq(JoinRequestDTO joinRequestDTO);

    int createJoinRequest(TeamJoinRequest teamJoinRequest);

    TeamJoinRequest checkJoinRequest(TeamJoinRequest invitationInfo);

    int updateJoinRequestState(TeamJoinRequest approvalInfo);

    List<JoinRequestDTO> findAllJoinRequests(JoinRequestDTO joinRequestDTO);
}
