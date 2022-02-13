package com.threeNerds.basketballDiary.mvc.service;

import com.threeNerds.basketballDiary.mvc.domain.User;
import com.threeNerds.basketballDiary.mvc.dto.UserDTO;
import com.threeNerds.basketballDiary.session.SessionConst;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class LoginServiceTest {

    @Autowired
    private LoginService loginService;

    @Test
    void checkLogin(){
        //given
        MockHttpSession session = new MockHttpSession();
        User user = new User();
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
        MockHttpSession session = new MockHttpSession();
        UserDTO userDTO = new UserDTO();
        //when
        session.setAttribute(SessionConst.LOGIN_MEMBER,userDTO);
        //then
        session.removeAttribute(SessionConst.LOGIN_MEMBER);
        UserDTO user = (UserDTO) session.getAttribute(SessionConst.LOGIN_MEMBER);
        assertThat(user).isNull();
    }
}