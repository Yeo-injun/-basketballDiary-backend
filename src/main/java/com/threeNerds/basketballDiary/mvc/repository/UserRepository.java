package com.threeNerds.basketballDiary.mvc.repository;

import com.threeNerds.basketballDiary.mvc.controller.LoginController;
import com.threeNerds.basketballDiary.mvc.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRepository {
    Long findSeq(String userId);
    User findUser(Long id);
    void saveUser(User user);
    User loginFindUser(LoginController.LoginUserRequest loginUserRequest);
    void updateUser(User user);
    void deleteUser(Long id);
}
