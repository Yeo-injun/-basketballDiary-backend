package com.threeNerds.basketballDiary.mvc.user.repository;

import com.threeNerds.basketballDiary.mvc.user.domain.User;
import com.threeNerds.basketballDiary.mvc.team.dto.TeamAuthDTO;
import com.threeNerds.basketballDiary.mvc.authUser.dto.PasswordUpdateDTO;
import com.threeNerds.basketballDiary.mvc.user.dto.CmnUserDTO;
import com.threeNerds.basketballDiary.mvc.authUser.dto.UpdateUserDTO;
import com.threeNerds.basketballDiary.mvc.user.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserRepository {
    /**********
     * SELECT
     **********/
    User findUser(Long userSeq);
    User findUserByUserId(String userId);

    List<UserDTO> findUserByUserNameOrEmail(CmnUserDTO findUserCond);
    List<TeamAuthDTO> findAuthList(User user);

    /**********
     * INSERT
     **********/
    int saveUser(User user);

    /**********
     * UPDATE
     **********/
    int updateUser(UpdateUserDTO user);
    int updatePassword(PasswordUpdateDTO passwordUpdateDTO);

    /**********
     * DELETE
     **********/
    int deleteUser(String id);

}
