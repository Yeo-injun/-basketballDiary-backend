package com.threeNerds.basketballDiary.mvc.service;

import com.threeNerds.basketballDiary.mvc.domain.User;
import com.threeNerds.basketballDiary.mvc.repository.UserRepository;
import com.threeNerds.basketballDiary.session.SessionConst;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @SpringBootTest 와 @Mock을 함께 사용하면 NoSuchMethodError 가 발생한다.
 * 원인 :
 * 해결책 :
 */
@ExtendWith(MockitoExtension.class)/**ExtendWith 어노테이션이 있어야 @Mock 이 재대로 작동한다**/
//@SpringBootTest
@Transactional
class UserServiceTest {

    MockHttpSession mockHttpSession;

    @Mock
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    User testUser;

    User modifyUser;

    @BeforeEach
    void setUpEach() {
        testUser = User.builder()
                .userId("User")
                .password("1111")
                .userName("Lee")
                .email("Lee00123@naver.com")
                .gender("M")
                .height(176.6)
                .weight(78.9)
                .regDate(LocalDate.now())
                .updateDate(LocalDate.now())
                .userRegYn("Y")
                .sidoCode("11")
                .sigunguCode("31")
                .build();

        modifyUser = User.builder()
                .userId("User")
                .password("9876")
                .userName("Kim")
                .email("Kim00122@naver.com")
                .gender("M")
                .height(176.6)
                .weight(78.9)
                .regDate(LocalDate.now())
                .updateDate(LocalDate.now())
                .userRegYn("Y")
                .sidoCode("11")
                .sigunguCode("311")
                .build();
        mockHttpSession = new MockHttpSession();
    }
    @Test
    void createUserTest(){
        //given
        when(userService.createMember(testUser)).thenReturn(1L);
        //when
        Long ret = userService.createMember(testUser);
        //then
        assertThat(ret).isEqualTo(1L);
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
        //assertEquals(retUser.getUserId(),testUser.getUserId());
    }

    @Test
    void modifyUserTest(){
        //given
        mockHttpSession.setAttribute(SessionConst.LOGIN_MEMBER,testUser.getUserId());
        doNothing().when(userService).updateUser(modifyUser);

        //when
        userService.updateUser(modifyUser);
//        User retUser = userService.findUser(modifyUser.getUserSeq());
        //then
//        Assertions.assertThat(testUser.getPassword()).isNotEqualTo(retUser.getPassword());
        verify(userService).updateUser(modifyUser);
    }
    @Test
    void deleteUserTest(){
        //given
        mockHttpSession.setAttribute(SessionConst.LOGIN_MEMBER,testUser.getUserId());
        doNothing().when(userService).deleteUser(testUser.getUserId());
        //when
        userService.deleteUser(testUser.getUserId());
        //then
        verify(userService).deleteUser(any(String.class));
    }
}