package com.threeNerds.basketballDiary.mvc.repository;

import com.threeNerds.basketballDiary.mvc.domain.User;
import com.threeNerds.basketballDiary.mvc.dto.AuthUserRequestDTO;
import com.threeNerds.basketballDiary.mvc.dto.loginUser.PasswordDTO;
import com.threeNerds.basketballDiary.mvc.dto.user.user.FindAllUserDTO;
import com.threeNerds.basketballDiary.mvc.dto.user.user.LoginUserDTO;
import com.threeNerds.basketballDiary.mvc.dto.user.user.UpdateUserDTO;
import com.threeNerds.basketballDiary.mvc.dto.user.user.UserDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserRepository {
    Long findSeq(String userId);
    User findUser(Long id);
    Long saveUser(User user);
    User loginFindUser(LoginUserDTO loginUserDTO);
    void updateUser(UpdateUserDTO user);
    void deleteUser(String id);
    void updatePassword(PasswordDTO passwordDTO);

    List<AuthUserRequestDTO> findAuthList(User user);
    List<UserDTO> findAllUser(FindAllUserDTO findAllUserDTO);

    // 사용자ID 중복확인 쿼리
    User findUserByUserId(User checkForDuplication);
}
