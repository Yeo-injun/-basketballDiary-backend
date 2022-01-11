package com.threeNerds.basketballDiary.mvc.repository;

import com.threeNerds.basketballDiary.mvc.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface UserRepository {
    Map<String,Object> findUser(Long id);
    void saveUser(User user);
}
