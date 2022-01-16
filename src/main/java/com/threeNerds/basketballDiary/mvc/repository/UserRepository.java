package com.threeNerds.basketballDiary.mvc.repository;

import com.threeNerds.basketballDiary.mvc.controller.LoginController;
import com.threeNerds.basketballDiary.mvc.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRepository {
    User findUser(Long id);
    Long saveUser(User user);
    User loginFindUser(LoginController.LoginUserRequest loginUserRequest);
}
