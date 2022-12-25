package com.threeNerds.basketballDiary.mvc.authUser.repository.dto;

import com.threeNerds.basketballDiary.mvc.authUser.dto.JoinRequestDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserTeamManagerRepository {

    List<JoinRequestDTO> findJoinRequestsByType(JoinRequestDTO joinRequestDTO);

}
