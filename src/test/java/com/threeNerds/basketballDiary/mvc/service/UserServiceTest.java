package com.threeNerds.basketballDiary.mvc.service;

import com.threeNerds.basketballDiary.mvc.domain.User;
import com.threeNerds.basketballDiary.mvc.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserServiceTest {

    @Autowired public UserService userService;

    public static final Long MEMBER_SEQ = 1L;
    @Test
    void createUserTest(){
        //given
        User user = getUser();
        //when
        Long id = userService.createMember(user);
        //then
        assertEquals(id,user.getUserSeq());
    }

    @Test
    void findUserTest(){
        //given
        User user = getUser();
        userService.createMember(user);
        //when
        User retUser = userService.findUser(MEMBER_SEQ);
        //then
        assertEquals(retUser.getUserSeq(),user.getUserSeq());
    }
    private User getUser() {
        User user = new User();
        user.setUserSeq(MEMBER_SEQ);
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