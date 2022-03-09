package com.threeNerds.basketballDiary.mvc.controller;

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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static com.threeNerds.basketballDiary.session.SessionConst.LOGIN_MEMBER;
import static com.threeNerds.basketballDiary.utils.HttpResponses.RESPONSE_CREATED;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/loginUser")
public class LoginUserController {

    private final UserService userService; // TODO UserTeamManagerService로 변경
    private final UserTeamManagerService userTeamManagerService;

    /**
     *  API020 : 농구팀 가입요청 보내기
     **/
    // TODO 클래스단위의 url 매핑정보 수정에 따라 root url 수정 필요
    // TODO 로그인 여부 체크하는 동작 필요 - checkLogin 어노테이션 적용 요망
    @PostMapping("/joinRequestTo/{teamSeq}")
    public ResponseEntity<?> sendJoinRequestToTeam(
            @SessionAttribute(value = LOGIN_MEMBER, required = false) SessionUser sessionUser,
            @PathVariable("teamSeq") Long teamSeq
    )
    {
        Long userSeq = sessionUser.getUserSeq();
        JoinRequestDTO joinRequest = new JoinRequestDTO()
                .teamSeq(teamSeq)
                .userSeq(userSeq);

        userTeamManagerService.sendJoinRequestToTeam(joinRequest);
        return RESPONSE_CREATED;
    }

    /**
     *  API022 : 농구팀 가입요청 및 초대 목록 조회
     **/
    @GetMapping("/joinRequestsAll")
    public ResponseEntity<?> searchJoinRequestsAll(
            @SessionAttribute(value = LOGIN_MEMBER, required = false) SessionUser sessionUser
    )
    {
        Long userSeq = sessionUser.getUserSeq();
        JoinRequestDTO joinRequestDTO = new JoinRequestDTO()
                .userSeq(userSeq);

        List<JoinRequestDTO> result = userTeamManagerService.searchJoinRequestsAll(joinRequestDTO);
        return ResponseEntity.ok(result);
    }

    /**
     * API025 회원정보 수정데이터 조회
     */
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
     * API026 회원수정 : update 를 수행한 후 update 된 객체를 리턴시켜주자 => 이래야 TEST CODE 작성시 정확히 update 가 되었는지 확인할 수 있다.
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
     * API028 회원탈퇴 : 회원탈퇴기능은 verify로 deleteUser 메소드가 호출되었는지 확인하는 방법 말고는 존재하지 않는다.
     *          만약 컬럼값 1개만 Y->N 으로 변경했더라면 객체간 비교를 해줄 수 있지만, 아에 테이블에서 삭제를 해버리는 이상 마땅한 방법이 없음
     */
    @DeleteMapping("/profile")
    public String deleteUser(@SessionAttribute(value = LOGIN_MEMBER,required = false) SessionUser sessionDTO){

        String id = sessionDTO.getUserId();

        userService.deleteUser(id);
        return "deleteOk";
    }
}
