package com.threeNerds.basketballDiary.mvc.controller;

import com.threeNerds.basketballDiary.interceptor.Auth;
import com.threeNerds.basketballDiary.mvc.domain.TeamJoinRequest;
import com.threeNerds.basketballDiary.mvc.domain.User;
import com.threeNerds.basketballDiary.mvc.dto.UserDTO;
import com.threeNerds.basketballDiary.mvc.service.UserService;
import com.threeNerds.basketballDiary.mvc.service.UserTeamManagerService;
import com.threeNerds.basketballDiary.session.SessionDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

import static com.threeNerds.basketballDiary.session.SessionConst.LOGIN_MEMBER;

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
// @RequestMapping("/api/user") // url수정요망
public class UserController {

    private final UserService userService;
    private final UserTeamManagerService userTeamManagerService;

    /**
     * 회원가입
     */
    @PostMapping("/user/new")
    public String create(@RequestBody @Valid UserDTO userDTO){
        User user = new User();
        user.setUserId(userDTO.getUserId());
        user.setPassword(userDTO.getPassword());
        user.setUserName(userDTO.getUserName());
        user.setEmail(userDTO.getEmail());
        user.setGender(userDTO.getGender());
        user.setWeight(userDTO.getWeight());

        LocalDate today = LocalDate.now();
        user.setRegDate(today);
        user.setUpdateDate(today);
        user.setUserRegYn("Y");

        user.setSidoCode(userDTO.getSidoCode());
        user.setSigunguCode(userDTO.getSigunguCode());
        user.setPositionCode(userDTO.getPositionCode());

        userService.createMember(user);
        return "createOk";
    }

    /**
     * 내정보 확인
     */
    @GetMapping("/user/myInfo")
    public UserDTO myInfo(@SessionAttribute(value = LOGIN_MEMBER, required = false) SessionDTO sessionDTO, UserDTO userDTO){
        Long id = sessionDTO.getUserSeq();
        User user = userService.findUser(id);
        UserDTO userDto = new UserDTO();

        BeanUtils.copyProperties(user,userDto);
        return userDto;
    }

    /**
     * 회원수정
     */
    @PutMapping("/user/modifyMyInfo")
    public String change(@SessionAttribute(value = LOGIN_MEMBER,required = false) SessionDTO sessionDTO,@RequestBody @Valid UserDTO userDTO){
        Long id = sessionDTO.getUserSeq();
        User user = userService.findUser(id);

        BeanUtils.copyProperties(userDTO,user);
        userService.updateUser(user);

        return "updateOk";
    }

    /**
     * 회원탈퇴
     */
    @DeleteMapping("/user/deleteUser")
    public String delete(@SessionAttribute(value = LOGIN_MEMBER,required = false) SessionDTO sessionDTO){
        Long id = sessionDTO.getUserSeq();
        userService.deleteUser(id);
        return "deleteOk";
    }

    @Auth(GRADE = 3L)
    @GetMapping("/testAnnotation/{teamId}")
    public void test(){
        log.info("Auth : 1");
    }

    /** 농구팀 가입요청 API */
    // TODO 클래스단위의 url 매핑정보 수정에 따라 root url 수정 필요
    // TODO 로그인 여부 체크하는 동작 필요 - checkLogin 어노테이션 적용 요망
    @PostMapping("/api/users/{userSeq}/joinRequestTo/{teamSeq}")
    public String joinRequestToTeam(
            @PathVariable("userSeq") String userSeq,
            @PathVariable("teamSeq") String teamSeq
    )
    {
        TeamJoinRequest teamJoinRequest = TeamJoinRequest.builder()
                                            .teamSeq(Long.parseLong(teamSeq))
                                            .userSeq(Long.parseLong(userSeq)).build();

        userTeamManagerService.sendJoinRequestToTeam(teamJoinRequest);
        return "Ok";
    }
}
