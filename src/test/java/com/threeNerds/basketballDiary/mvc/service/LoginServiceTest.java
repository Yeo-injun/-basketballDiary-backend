package com.threeNerds.basketballDiary.mvc.service;

import com.threeNerds.basketballDiary.mvc.domain.User;
import com.threeNerds.basketballDiary.mvc.dto.UserDTO;
import com.threeNerds.basketballDiary.session.SessionConst;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class LoginServiceTest {

    MockHttpSession session;
    @Autowired
    private LoginService loginService;

    User user;

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
    }
    @Test
    void checkLogin(){
        //given

        //when
        session.setAttribute(SessionConst.LOGIN_MEMBER,user);
        User result = (User)session.getAttribute(SessionConst.LOGIN_MEMBER);
        //then
            //세션 확인
        assertThat(result).isEqualTo(user);
            //세션 만료
        session.removeAttribute(SessionConst.LOGIN_MEMBER);
        User result2 = (User)session.getAttribute(SessionConst.LOGIN_MEMBER);
        assertThat(result2).isNull();
    }
    @Test
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