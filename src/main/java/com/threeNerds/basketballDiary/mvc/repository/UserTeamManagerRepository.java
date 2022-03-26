package com.threeNerds.basketballDiary.mvc.repository;

import com.threeNerds.basketballDiary.mvc.dto.loginUser.userTeamManager.JoinRequestDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserTeamManagerRepository {

    List<JoinRequestDTO> findJoinRequestsByType(JoinRequestDTO joinRequestDTO);

}
