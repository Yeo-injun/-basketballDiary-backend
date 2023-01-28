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
    User findUser(Long userSeq);
    User findUserByUserId(String userId);

    List<UserDTO> findUserByUserNameOrEmail(CmnUserDTO findUserCond);
    List<TeamAuthDTO> findAuthList(User user);

    Long saveUser(User user);
    void updateUser(UpdateUserDTO user);
    void deleteUser(String id);
    void updatePassword(PasswordUpdateDTO passwordUpdateDTO);
}
