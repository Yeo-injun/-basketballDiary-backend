package com.threeNerds.basketballDiary.mvc.controller;

import com.threeNerds.basketballDiary.interceptor.Auth;
import com.threeNerds.basketballDiary.mvc.domain.User;
import com.threeNerds.basketballDiary.mvc.dto.user.UserDTO;
import com.threeNerds.basketballDiary.mvc.service.UserService;
import com.threeNerds.basketballDiary.mvc.service.UserTeamManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

import static com.threeNerds.basketballDiary.utils.HttpResponses.*;

/**
 * ... 수행하는 Controller
 * @author 책임자 작성
 *
 * issue and history
 * <pre>
 * 2022.02.08 여인준 : 소스코드 생성
 * </pre>
 */

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserTeamManagerService userTeamManagerService;

    /**
     * API029 회원가입
     */
    @PostMapping("/registration")
    public ResponseEntity<?> createUser(@RequestBody @Valid UserDTO userDTO){

        User user = User.builder()
                .userId(userDTO.getUserId())
                .password(userDTO.getPassword())
                .userName(userDTO.getUserName())
                .positionCode(userDTO.getPositionCode())
                .email(userDTO.getEmail())
                .gender(userDTO.getGender())
                .birthYmd(userDTO.getBirthYmd())
                .height(userDTO.getHeight())
                .weight(userDTO.getWeight())
                .regDate(LocalDate.now())
                .updateDate(LocalDate.now())
                .userRegYn("Y")
                .sidoCode(userDTO.getSidoCode())
                .sigunguCode(userDTO.getSigunguCode())
                .build();

        userService.createMember(user);
        return RESPONSE_OK;
    }

    @Auth(GRADE = 3L)
    @GetMapping("/testAnnotation/{teamId}")
    public void test(){
        log.info("Auth : 1");
    }

    @Auth(GRADE = 2L)
    @GetMapping("/testAnnotation2/{teamId}")
    public void test2(){
        log.info("Auth : 2");
    }
}
