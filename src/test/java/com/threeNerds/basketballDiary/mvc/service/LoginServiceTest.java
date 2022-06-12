package com.threeNerds.basketballDiary.mvc.service;

import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.mvc.domain.User;
import com.threeNerds.basketballDiary.mvc.dto.user.user.LoginUserDTO;
import com.threeNerds.basketballDiary.mvc.dto.user.user.UserDTO;
import com.threeNerds.basketballDiary.mvc.repository.UserRepository;
import com.threeNerds.basketballDiary.session.SessionConst;
import com.threeNerds.basketballDiary.session.SessionUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class LoginServiceTest {

    MockHttpSession session;
    @Autowired
    private LoginService loginService;
    @Autowired
    private UserRepository userRepository;

    User user;
    User wrongUser;

    @BeforeEach
    void setUpEach(){
        user = User.builder()
                .userId("gch03915")
                .password("test01")
                .positionCode("21")
                .email("keodong123@naver.com")
                .gender("M")
                .height(123.12)
                .weight(68.6)
                .regDate(LocalDate.now())
                .updateDate(LocalDate.now())
                .userRegYn("Y")
                .sidoCode("11")
                .sigunguCode("31")
                .build();

        session = new MockHttpSession();
        userRepository.saveUser(user);
    }
    @Test
    @DisplayName("정상적인 로그인 시도")
    void checkLogin(){
        //given
        LoginUserDTO loginUserDTO = LoginUserDTO.createLoginUserDTO(user);
        //when
        SessionUser sessionUser = loginService.login(loginUserDTO);
        //then
        assertThat(sessionUser.getUserId()).isEqualTo(user.getUserId());
    }
    @Test
    @DisplayName("비정상적인 로그인 시도 : 아이디 불일치")
    void wrongLogin_Id(){
        //given
        LoginUserDTO loginUserDTO = LoginUserDTO.createLoginUserDTO(user);
        //when
        loginUserDTO.userId("WrongId");
        //then
        assertThatThrownBy(()->loginService.login(loginUserDTO))
                .isInstanceOf(CustomException.class);
    }
    @Test
    @DisplayName("비정상적인 로그인 시도 : 비밀번호 불일치")
    void wrongLogin_Password(){
        //given
        LoginUserDTO loginUserDTO = LoginUserDTO.createLoginUserDTO(user);
        //when
        loginUserDTO.password("test02");
        //then
        assertThatThrownBy(()->loginService.login(loginUserDTO))
                .isInstanceOf(CustomException.class);
    }
    @Test
    @DisplayName("로그아웃 : 세션 삭제 확인")
    void checkLogout(){
        //given
        UserDTO userDTO = new UserDTO();
        //when
        session.setAttribute(SessionConst.LOGIN_MEMBER,userDTO);
        //then
        session.removeAttribute(SessionConst.LOGIN_MEMBER);
        UserDTO user = (UserDTO) session.getAttribute(SessionConst.LOGIN_MEMBER);
        assertThat(user).isNull();
    }
}