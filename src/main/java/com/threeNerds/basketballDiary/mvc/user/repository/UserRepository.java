package com.threeNerds.basketballDiary.mvc.user.repository;

import com.threeNerds.basketballDiary.mvc.user.domain.User;
import com.threeNerds.basketballDiary.mvc.team.dto.TeamAuthDTO;
import com.threeNerds.basketballDiary.mvc.authUser.dto.PasswordUpdateDTO;
import com.threeNerds.basketballDiary.mvc.user.dto.CmnUserDTO;
import com.threeNerds.basketballDiary.mvc.authUser.dto.UpdateUserDTO;
import com.threeNerds.basketballDiary.mvc.user.dto.UserDTO;
import com.threeNerds.basketballDiary.mvc.user.dto.UserInqCondDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserRepository {
    /**********
     * SELECT
     **********/
    User findUser(Long userSeq);
    User findUserByUserId(String userId);
    List<UserDTO> findAllUsersExcludingTeamMemberByUserNameOrEmail(UserInqCondDTO inqCond);

    /**********
     * INSERT
     **********/
    int saveUser(User user);

    /**********
     * UPDATE
     **********/
    int updateProfile( User profile );
    int updatePassword(PasswordUpdateDTO passwordUpdateDTO);

    /**********
     * DELETE
     **********/
    int deleteUser(String id);

}
