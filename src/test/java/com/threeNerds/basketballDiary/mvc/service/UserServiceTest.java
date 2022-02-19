package com.threeNerds.basketballDiary.mvc.service;

import com.threeNerds.basketballDiary.mvc.domain.User;
import com.threeNerds.basketballDiary.session.SessionConst;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Transactional
class UserServiceTest {

    MockHttpSession mockHttpSession;

    @MockBean
    private UserService userService;

    User testUser;

    User modifyUser;

    @BeforeEach
    void setUpEach() {
        testUser = new User.Builder("User","1111","Lee","Lee00123@naver.com","Y","M",176.6,78.9)
                .withPositionCode("21")
                .withRegDate(LocalDate.now())
                .withUpdateDate(LocalDate.now())
                .withSidoCode("11")
                .withSigunduCode("123")
                .build();

        modifyUser = new User.Builder("User","9876","Kim","Kim00122@naver.com","Y","M",176.6,78.9)
                .withPositionCode("22")
                .withRegDate(LocalDate.now())
                .withUpdateDate(LocalDate.now())
                .withSidoCode("11")
                .withSigunduCode("322")
                .build();
        mockHttpSession = new MockHttpSession();
    }
    @Test
    void createUserTest(){
        //given
        doNothing().when(userService).createMember(testUser);
        //when
        userService.createMember(testUser);
        //then
        verify(userService).createMember(testUser);
    }

    @Test
    void findUserTest(){
        //given
        Long retSeq = userService.findSeq(testUser.getUserId());
        when(userService.findUser(retSeq)).thenReturn(testUser);
        //when
        User retUser = userService.findUser(retSeq);
        //then
        assertEquals(retUser.getUserId(),testUser.getUserId());
    }

    @Test
    void modifyUserTest(){
        //given
        mockHttpSession.setAttribute(SessionConst.LOGIN_MEMBER,testUser.getUserId());
        doNothing().when(userService).updateUser(modifyUser);

        //when
        userService.updateUser(modifyUser);
        User retUser = userService.findUser(modifyUser.getUserSeq());
        //then
        Assertions.assertThat(testUser.getPassword()).isNotEqualTo(retUser.getPassword());
        verify(userService).updateUser(modifyUser);
    }
    @Test
    void deleteUserTest(){
        //given
        mockHttpSession.setAttribute(SessionConst.LOGIN_MEMBER,testUser);
        //when
        doNothing().when(userService).deleteUser(testUser.getUserSeq());
        //then
        verify(userService).deleteUser(any(Long.class));
    }
}