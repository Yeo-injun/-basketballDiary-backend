package com.threeNerds.basketballDiary.mvc.service;

import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.mvc.auth.service.AuthService;
import com.threeNerds.basketballDiary.mvc.user.domain.User;
import com.threeNerds.basketballDiary.mvc.auth.dto.LoginUserDTO;
import com.threeNerds.basketballDiary.mvc.user.dto.UserDTO;
import com.threeNerds.basketballDiary.mvc.user.repository.UserRepository;
import com.threeNerds.basketballDiary.session.SessionUser;
import com.threeNerds.basketballDiary.utils.EncryptUtil;
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
    private AuthService loginService;

    @Autowired
    private UserRepository userRepository;

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
        userRepository.saveUser(user);
    }
    @Test
    @DisplayName("정상적인 로그인 시도")
    void checkLogin(){
        //given
//        LoginUserDTO loginUserDTO = new LoginUserDTO()
//                .userId(user.getUserId())
//                .password(user.getPassword());
//        //when
//        SessionUser sessionUser = loginService.login(loginUserDTO);
//        //then
//        assertThat(sessionUser.getUserId()).isEqualTo(user.getUserId());
    }
    @Test
    @DisplayName("비정상적인 로그인 시도 : 아이디 불일치")
    void wrongLogin_Id(){
        //given
        LoginUserDTO loginUserDTO = new LoginUserDTO()
                .userId(user.getUserId())
                .password(user.getPassword());
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
        LoginUserDTO loginUserDTO = new LoginUserDTO()
                .userId(user.getUserId())
                .password(user.getPassword());
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
        session.setAttribute("loginUser",userDTO);
        //then
        session.removeAttribute("loginUser");
        UserDTO user = (UserDTO) session.getAttribute("loginUser");
        assertThat(user).isNull();
    }

    @Test
    void 비밀번호_생성() {
        String[] userIdArr = {"test02", "test03", "test04", "test05", "master",};
        for (String userId : userIdArr ) {
            String encryptPwd = EncryptUtil.getEncrypt("1234", userId);
            System.out.println( "UserId : " + userId + " / 비밀번호 : " + encryptPwd );
        }
    }
}