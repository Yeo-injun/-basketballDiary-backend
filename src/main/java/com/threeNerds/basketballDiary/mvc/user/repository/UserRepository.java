package com.threeNerds.basketballDiary.mvc.user.repository;

import com.threeNerds.basketballDiary.mvc.user.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserRepository {
    /**********
     * SELECT
     **********/
    User findUser(Long userSeq);
    User findUserByUserId(String userId);

    /**********
     * INSERT
     **********/
    int saveUser(User user);

    /**********
     * UPDATE
     **********/
    int updateProfile( User profileUpdateUser );
    int updatePassword( User passwordUpdateUser );

    /**********
     * DELETE
     **********/
    int deleteUser( Long userSeq );

}
