package com.threeNerds.basketballDiary.mvc.user.controller;

import com.threeNerds.basketballDiary.mvc.user.dto.CmnUserDTO;
import com.threeNerds.basketballDiary.mvc.user.dto.UserDTO;
import com.threeNerds.basketballDiary.mvc.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ... 수행하는 Controller
 * @author 책임자 작성
 *
 * issue and history
 * <pre>
 * 2022.02.08 여인준 : 소스코드 생성
 * 2022.03.13 이성주 : LoginController 통합
 * </pre>
 */

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    /**
     * API006 사용자 검색
     */
    @GetMapping
    public ResponseEntity<?> searchUsers (
            // @RequestParam에 required=false가 없으면  get요청시 쿼리스트링이 반드시 있어야 함.
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) String email
    ){
        CmnUserDTO findUserCond = new CmnUserDTO()
                                        .userName(userName)
                                        .email(email);
        // TODO Response객체 만들어서 리턴해주기
        List<UserDTO> findUserList = userService.searchUsers(findUserCond);
        return ResponseEntity.ok().body(findUserList);
    }

}
