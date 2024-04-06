package com.threeNerds.basketballDiary.mvc.user.repository.dto;

import com.threeNerds.basketballDiary.mvc.user.domain.User;
import com.threeNerds.basketballDiary.mvc.user.dto.UserDTO;
import com.threeNerds.basketballDiary.mvc.user.dto.UserInqCondDTO;
import com.threeNerds.basketballDiary.mvc.user.dto.UserQueryCondDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserQueryRepository {
    /**
     * 팀원을 제외한 사용자 목록 조회 (TODO 적용 예정 페이징 반영 )
     */
    List<UserDTO> findAllPagingUsersExcludingTeamMembers( UserQueryCondDTO inqCond );
}
