package com.threeNerds.basketballDiary.mvc.user.mapper;

import com.threeNerds.basketballDiary.mvc.user.mapper.dto.UserDTO;

import com.threeNerds.basketballDiary.mvc.user.mapper.dto.UserQueryCondDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    /**
     * 팀원을 제외한 사용자 목록 조회
     */
    List<UserDTO> findPaginationUsersWithoutTeamMembers( UserQueryCondDTO inqCond );

    int findTotalCountUsersWithoutTeamMembers( UserQueryCondDTO inqCond );

}
