package com.threeNerds.basketballDiary.mvc.service;

import com.threeNerds.basketballDiary.mvc.domain.User;
import com.threeNerds.basketballDiary.mvc.dto.user.CmnUserDTO;
import com.threeNerds.basketballDiary.mvc.dto.user.user.UserDTO;
import com.threeNerds.basketballDiary.mvc.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * @SpringBootTest 와 @Mock을 함께 사용하면 NoSuchMethodError 가 발생한다.
 * 원인 : 못찾음
 * 해결책 : @SpringBootTest 를 사용할 거면 @Mockmvc 를 이용, 그게 아니라면 @Mock 과 @InjectMocks를 사용
 */
//@ExtendWith(MockitoExtension.class)/**ExtendWith 어노테이션이 있어야 @Mock 이 재대로 작동한다**/
@SpringBootTest
@Transactional
class UserServiceTest {

    MockHttpSession mockHttpSession;

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    CmnUserDTO testUser;

    @BeforeEach
    void setUpEach() {
        testUser = new CmnUserDTO()
                .userId("User")
                .password("1111")
                .userName("Lee")
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

        //when
        Long userSeq = userService.createMember(testUser);
        UserDTO findByUser = userService.findUser(userSeq);
        //then
        assertThat(userSeq).isEqualTo(findByUser.getUserSeq());
    }

    @Test
    void deleteUserTest(){
        //given

        //when
        userService.deleteUser(testUser.getUserId());
        //then
        verify(userRepository).deleteUser(testUser.getUserId());
    }
}