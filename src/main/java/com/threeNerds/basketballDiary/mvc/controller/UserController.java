package com.threeNerds.basketballDiary.mvc.controller;

import com.threeNerds.basketballDiary.interceptor.Auth;
import com.threeNerds.basketballDiary.mvc.domain.User;
import com.threeNerds.basketballDiary.mvc.dto.JoinRequestDTO;
import com.threeNerds.basketballDiary.mvc.dto.ResponseMyTeamProfileDTO;
import com.threeNerds.basketballDiary.mvc.dto.UserDTO;
import com.threeNerds.basketballDiary.mvc.service.TeamMemberService;
import com.threeNerds.basketballDiary.mvc.service.UserService;
import com.threeNerds.basketballDiary.mvc.service.UserTeamManagerService;
import com.threeNerds.basketballDiary.session.SessionUser;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
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
@RequestMapping("/api/user") // url수정요망
public class UserController {

    private final UserService userService;
    private final UserTeamManagerService userTeamManagerService;

    /**
     * 회원가입
     */
    @PostMapping("/new")
    public String createUser(@RequestBody @Valid UserDTO userDTO){

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
        return "createOk";
    }

    @GetMapping("/profile")
    public UserDTO myInfo(
            @SessionAttribute(value = LOGIN_MEMBER, required = false) SessionUser sessionDTO,
            UserDTO userDTO){

        Long id = sessionDTO.getUserSeq();

        User user = userService.findUser(id);
        UserDTO userDto = new UserDTO();

        BeanUtils.copyProperties(user,userDto);
        return userDto;
    }

    /**
     * 회원수정 : update 를 수행한 후 update 된 객체를 리턴시켜주자 => 이래야 TEST CODE 작성시 정확히 update 가 되었는지 확인할 수 있다.
     */

    @PutMapping("/profile")
    public String updateUser(
            @SessionAttribute(value = LOGIN_MEMBER,required = false) SessionUser sessionDTO,
            @RequestBody @Valid UserDTO userDTO) {

        Long id = sessionDTO.getUserSeq();

        User user = userService.findUser(id);

        BeanUtils.copyProperties(userDTO,user);
        userService.updateUser(user);

        return "updateOk";
    }

    /**
     * 회원탈퇴 : 회원탈퇴기능은 verify로 deleteUser 메소드가 호출되었는지 확인하는 방법 말고는 존재하지 않는다.
     *          만약 컬럼값 1개만 Y->N 으로 변경했더라면 객체간 비교를 해줄 수 있지만, 아에 테이블에서 삭제를 해버리는 이상 마땅한 방법이 없음
     */

    @DeleteMapping("/profile")
    public String deleteUser(@SessionAttribute(value = LOGIN_MEMBER,required = false) SessionUser sessionDTO){

        String id = sessionDTO.getUserId();

        userService.deleteUser(id);
        return "deleteOk";
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

    /****************************************************************************************************************/

    /**
     *  API020 : 농구팀 가입요청 보내기
     **/
    // TODO 클래스단위의 url 매핑정보 수정에 따라 root url 수정 필요
    // TODO 로그인 여부 체크하는 동작 필요 - checkLogin 어노테이션 적용 요망
    @PostMapping("/{userSeq}/joinRequestTo/{teamSeq}")
    public String joinRequestToTeam(
            @PathVariable("userSeq") Long userSeq,
            @PathVariable("teamSeq") Long teamSeq)
    {
        JoinRequestDTO joinRequest = new JoinRequestDTO()
                                            .teamSeq(teamSeq)
                                            .userSeq(userSeq);

        userTeamManagerService.sendJoinRequestToTeam(joinRequest);
        return "Ok";
    }

    // TODO HTTP메세지 생성 메서드 추가해주기
    // TODO remote repo에 PUSH하기 위한 수정
}
