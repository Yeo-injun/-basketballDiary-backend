package com.threeNerds.basketballDiary.mvc.service;

import com.threeNerds.basketballDiary.mvc.auth.dto.CreateUserDTO;
import com.threeNerds.basketballDiary.mvc.auth.service.AuthService;
import com.threeNerds.basketballDiary.mvc.authUser.service.AuthUserService;
import com.threeNerds.basketballDiary.mvc.user.dto.UserDTO;
import com.threeNerds.basketballDiary.mvc.user.repository.UserRepository;
import com.threeNerds.basketballDiary.mvc.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.mock.web.MockHttpSession;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.Mockito.verify;

/**
 * @SpringBootTest 와 @Mock을 함께 사용하면 NoSuchMethodError 가 발생한다.
 * 원인 : 못찾음
 * 해결책 : @SpringBootTest 를 사용할 거면 @Mockmvc 를 이용, 그게 아니라면 @Mock 과 @InjectMocks를 사용
 */
@ExtendWith(MockitoExtension.class)/**ExtendWith 어노테이션이 있어야 @Mock 이 재대로 작동한다**/
@Transactional
class UserServiceTest {

    private MockHttpSession mockHttpSession;

    @InjectMocks
    UserService userService;
    @InjectMocks
    AuthService authService;
    @InjectMocks
    AuthUserService authUserService;
    @Mock
    UserRepository userRepository;

    CreateUserDTO testUser;

    @BeforeEach
    void setUpEach() {
        testUser = new CreateUserDTO()
                .userId("User")
                .password("1111")
//                .userName("Lee")
                .name("Lee")
                .email("Lee00123@naver.com")
                .gender("M")
                .height(176.6)
                .weight(78.9)
                .sidoCode("11")
                .sigunguCode("31");
        mockHttpSession = new MockHttpSession();
    }
    @Test
    @DisplayName("회원가입")
    void createUserTest(){
        //given
//        when(userService.createMember(testUser)).thenReturn("User");
        //when
//        Long userSeq = authService.createMember(testUser);
//        UserDTO findByUser = userService.findUser(userSeq);
        //then
//        assertThat(userSeq).isEqualTo("User");
    }

    @Test
    void deleteUserTest(){
        //given

        //when
//        authUserService.(testUser.getUserId());
        //then
//        verify(userRepository).deleteUser(testUser.getUserId());
    }
}