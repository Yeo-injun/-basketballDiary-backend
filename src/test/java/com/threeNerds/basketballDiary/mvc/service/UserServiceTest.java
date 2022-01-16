package com.threeNerds.basketballDiary.mvc.service;

import com.threeNerds.basketballDiary.mvc.domain.User;
import com.threeNerds.basketballDiary.mvc.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired public UserService userService;

    @Test
    void createUserTest(){
        //given
        User user = getUser();
        //when
        Long id = userService.createMember(user);
        //then
        assertEquals(userService.findUser(id).getUserId(),user.getUserId());
    }

    @Test
    void findUserTest(){
        //given
        User user = getUser();
        Long id = userService.createMember(user);
        //when
        User retUser = userService.findUser(id);
        //then
        assertEquals(retUser.getUserSeq(),id);
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