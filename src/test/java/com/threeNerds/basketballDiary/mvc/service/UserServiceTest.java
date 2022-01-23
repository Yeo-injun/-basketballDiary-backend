package com.threeNerds.basketballDiary.mvc.service;

import com.threeNerds.basketballDiary.mvc.domain.User;
import com.threeNerds.basketballDiary.mvc.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extensions;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired private UserService userService;

    @Test
    void createUserTest(){
        //given
        User user = getUser();
        //when
        userService.createMember(user);
        Long retSeq = userService.findSeq(user.getUserId());
        //then
        assertEquals(userService.findUser(retSeq).getUserId(),user.getUserId());
    }

    @Test
    void findUserTest(){
        //given
        User user = getUser();
        userService.createMember(user);
        Long retSeq = userService.findSeq(user.getUserId());
        //when
        User retUser = userService.findUser(retSeq);
        //then
        assertEquals(retUser.getUserSeq(),retSeq);
    }

    @Test
    void modifyUserTest(){
        //given
        User user = getUser();
        String preName = user.getUserName();
        userService.createMember(user);
        //when
        user.setUserName("kim");
        userService.updateUser(user);
        User retUser = userService.findUser(user.getUserSeq());
        //then
        assertNotEquals(retUser.getUserName(),preName);
    }
    @Test
    void deleteUserTest(){
        //given
        User user = getUser();
        Long retSeq = userService.findSeq(user.getUserId());
        //when
        userService.deleteUser(retSeq);
        //then
        assertNull(userService.findUser(retSeq));
    }
    private User getUser() {
        User user = new User();
        user.setUserId("User");
        user.setPassword("1111");
        user.setUserName("Lee");
        user.setPositionCode("22");
        user.setEmail("User@naver.com");
        user.setGender("M");
        user.setHeight(176.6);
        user.setWeight(78.9);
        user.setRegDate(LocalDate.now());
        user.setUpdateDate(LocalDate.now());
        user.setUserRegYn("Y");
        user.setSidoCode("11");
        user.setSigunguCode("173");
        return user;
    }
}