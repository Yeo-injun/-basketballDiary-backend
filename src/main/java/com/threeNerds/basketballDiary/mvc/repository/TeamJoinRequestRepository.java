package com.threeNerds.basketballDiary.mvc.repository;

import com.threeNerds.basketballDiary.mvc.domain.TeamJoinRequest;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TeamJoinRequestRepository {

    int createJoinRequest(TeamJoinRequest teamJoinRequest);

    TeamJoinRequest checkJoinRequest(TeamJoinRequest invitationInfo);
}
