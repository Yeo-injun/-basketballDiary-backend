package com.threeNerds.basketballDiary.mvc.repository;

import com.threeNerds.basketballDiary.mvc.domain.User;
import com.threeNerds.basketballDiary.mvc.dto.AuthUserRequestDTO;
import com.threeNerds.basketballDiary.mvc.dto.loginUser.CmnLoginUserDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserRepository {
    Long findSeq(String userId);
    User findUser(Long id);
    Long saveUser(User user);
    User loginFindUser(CmnLoginUserDTO cmnLoginUserDTO);
    void updateUser(User user);
    void deleteUser(String id);

    List<AuthUserRequestDTO> findAuthList(CmnLoginUserDTO cmnLoginUserDTO);
}
