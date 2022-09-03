package com.threeNerds.basketballDiary.mvc.repository;

import com.threeNerds.basketballDiary.mvc.domain.User;
import com.threeNerds.basketballDiary.mvc.dto.TeamAuthDTO;
import com.threeNerds.basketballDiary.mvc.dto.loginUser.PasswordUpdateDTO;
import com.threeNerds.basketballDiary.mvc.dto.user.CmnUserDTO;
import com.threeNerds.basketballDiary.mvc.dto.user.user.FindAllUserDTO;
import com.threeNerds.basketballDiary.mvc.dto.user.user.LoginUserDTO;
import com.threeNerds.basketballDiary.mvc.dto.user.user.UpdateUserDTO;
import com.threeNerds.basketballDiary.mvc.dto.user.user.UserDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserRepository {
    Long findSeq(String userId);
    User findUser(Long userSeq);
    User findUserByUserId(String userId);
    User loginFindUser(LoginUserDTO loginUserDTO); // TODO 삭제 검토

    List<UserDTO> findAllUser(FindAllUserDTO findAllUserDTO);
    List<UserDTO> findUserByUserNameOrEmail(CmnUserDTO findUserCond);
    List<TeamAuthDTO> findAuthList(User user);

    Long saveUser(User user);
    void updateUser(UpdateUserDTO user);
    void deleteUser(String id);
    void updatePassword(PasswordUpdateDTO passwordUpdateDTO);
}
