package com.threeNerds.basketballDiary.mvc.user.mapper;

import com.threeNerds.basketballDiary.mvc.user.mapper.dto.JoinRequestDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserTeamJoinRequestMapper {

    List<JoinRequestDTO> findJoinRequestsByType(JoinRequestDTO joinRequestDTO);

}
