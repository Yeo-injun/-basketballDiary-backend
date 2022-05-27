package com.threeNerds.basketballDiary.mvc.service;

import com.threeNerds.basketballDiary.mvc.domain.User;
import com.threeNerds.basketballDiary.mvc.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
@ExtendWith(MockitoExtension.class)/**ExtendWith 어노테이션이 있어야 @Mock 이 재대로 작동한다**/
//@SpringBootTest
@Transactional
class UserServiceTest {

    MockHttpSession mockHttpSession;

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    User testUser;

    User testUser2;

    @BeforeEach
    void setUpEach() {
        testUser = User.builder()
                .userSeq(1L)
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

        testUser2 = User.builder()
                .userSeq(1L)
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
        when(userRepository.saveUser(testUser)).thenReturn(1L);
        //when
        Long id = userService.createMember(testUser);
        //then
        assertThat(id).isEqualTo(1L);
    }

    @Test
    void findUserTest(){
        //given
        when(userRepository.findUser(1L)).thenReturn(testUser);
        //when
        User user = userService.findUser(1L);
        //then
        assertThat(user).isNotEqualTo(testUser2);
    }

    @Test
    void deleteUserTest(){
        //given
        doNothing().when(userRepository).deleteUser("Uesr");
        //when
        userService.deleteUser(testUser.getUserId());
        //then
        verify(userRepository).deleteUser(testUser.getUserId());
    }
}