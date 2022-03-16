package com.threeNerds.basketballDiary.mvc.controller;

import com.threeNerds.basketballDiary.mvc.domain.User;
import com.threeNerds.basketballDiary.mvc.dto.loginUser.userTeamManager.JoinRequestDTO;
import com.threeNerds.basketballDiary.mvc.dto.loginUser.CmnLoginUserDTO;
import com.threeNerds.basketballDiary.mvc.dto.user.UserDTO;
import com.threeNerds.basketballDiary.mvc.service.UserService;
import com.threeNerds.basketballDiary.mvc.service.UserTeamManagerService;
import com.threeNerds.basketballDiary.session.SessionUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static com.threeNerds.basketballDiary.session.SessionConst.LOGIN_MEMBER;
import static com.threeNerds.basketballDiary.utils.HttpResponses.RESPONSE_CREATED;
import static com.threeNerds.basketballDiary.utils.HttpResponses.RESPONSE_OK;

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
    // TODO 로그인 여부 체크하는 동작 필요 - checkLogin 어노테이션 적용 요망
    @PostMapping("/joinRequestTo/{teamSeq}")
    public ResponseEntity<?> sendJoinRequestToTeam(
            @SessionAttribute(value = LOGIN_MEMBER, required = false) SessionUser sessionUser,
            @PathVariable Long teamSeq
    )
    {
        Long userSeq = sessionUser.getUserSeq();
        CmnLoginUserDTO loginUserDTO = new CmnLoginUserDTO()
                .teamSeq(teamSeq)
                .userSeq(userSeq);

        userTeamManagerService.sendJoinRequestToTeam(loginUserDTO);
        return RESPONSE_CREATED;
    }

    /**
     *  API022 : 농구팀 가입요청 목록 조회
     *  22.03.13 인준 : API022 세분화 - 가입요청 및 초대 목록을 하나의 API콜로 가져오는 것에서 API 2개를 콜해서 가져오는 구조로 변경
     **/
    @GetMapping("/joinRequestsTo")
    public ResponseEntity<?> getJoinRequestsTo (
            @SessionAttribute(value = LOGIN_MEMBER, required = false) SessionUser sessionUser
    ) {
        Long userSeq = sessionUser.getUserSeq();
        CmnLoginUserDTO loginUserDTO = new CmnLoginUserDTO()
                .userSeq(userSeq);

        List<JoinRequestDTO> result = userTeamManagerService.getJoinRequestsTo(loginUserDTO);
        // TODO ResponseDTO로 감싸서 보내주기
        return ResponseEntity.ok(result);
    }

    /**
     * API023 : 팀 가입요청 취소
     */
    @DeleteMapping("/joinRequestsTo/{teamJoinRequestSeq}")
    public ResponseEntity<?> cancelJoinReqeust(
            @SessionAttribute(value = LOGIN_MEMBER, required = false) SessionUser sessionUser,
            @PathVariable Long teamJoinRequestSeq
    ) {
        CmnLoginUserDTO loginUserDTO = new CmnLoginUserDTO()
                .teamJoinRequestSeq(teamJoinRequestSeq)
                .userSeq(sessionUser.getUserSeq());

        userTeamManagerService.cancelJoinRequest(loginUserDTO);

        return RESPONSE_OK;
    }

    /**
     * API024 : 팀 초대 승인
     */
    @PutMapping("/joinRequestsFrom/{teamJoinRequestSeq}/approval")
    public ResponseEntity<?> approveInvitation (
            @SessionAttribute(value = LOGIN_MEMBER, required = false) SessionUser sessionUser,
            @PathVariable Long teamJoinRequestSeq
    ) {
        Long userSeq = sessionUser.getUserSeq();
        CmnLoginUserDTO loginUserDTO = new CmnLoginUserDTO()
                .teamJoinRequestSeq(teamJoinRequestSeq)
                .userSeq(userSeq);

        userTeamManagerService.approveInvitation(loginUserDTO);
        return RESPONSE_OK;
    }

    /**
     *  API032 : 농구팀 초대 목록 조회
     *  22.03.13 인준 : API022 세분화 - 가입요청 및 초대 목록을 하나의 API콜로 가져오는 것에서 API 2개를 콜해서 가져오는 구조로 변경
     **/
    @GetMapping("/joinRequestsFrom")
    public ResponseEntity<?> getJoinRequestsFrom(
            @SessionAttribute(value = LOGIN_MEMBER, required = false) SessionUser sessionUser
    )
    {
        Long userSeq = sessionUser.getUserSeq();
        CmnLoginUserDTO loginUserDTO = new CmnLoginUserDTO()
                .userSeq(userSeq);

        List<JoinRequestDTO> result = userTeamManagerService.getJoinRequestsFrom(loginUserDTO);
        // TODO ResponseDTO로 감싸서 보내주기
        return ResponseEntity.ok(result);
    }



    /**끝 인준 API **************************************************************************************************************/

    /**
     * API025 회원정보 수정데이터 조회
     */
    @GetMapping("/profile")
    public ResponseEntity<UserDTO> myInfo(
            @SessionAttribute(value = LOGIN_MEMBER, required = false) SessionUser sessionDTO,
            UserDTO userDTO){

        Long id = sessionDTO.getUserSeq();

        User user = userService.findUser(id);
        UserDTO userDto = new UserDTO();

        BeanUtils.copyProperties(user,userDto);
        return ResponseEntity.ok(userDto);
    }

    /**
     * API026 회원수정 : update 를 수행한 후 update 된 객체를 리턴시켜주자 => 이래야 TEST CODE 작성시 정확히 update 가 되었는지 확인할 수 있다.
     */
    @PutMapping("/profile")
    public ResponseEntity<?> updateUser(
            @SessionAttribute(value = LOGIN_MEMBER,required = false) SessionUser sessionDTO,
            @RequestBody @Valid UserDTO userDTO) {

        Long id = sessionDTO.getUserSeq();

        User user = userService.findUser(id);

        BeanUtils.copyProperties(userDTO,user);
        userService.updateUser(user);

        return RESPONSE_OK;
    }

    /**
     * API028 회원탈퇴 : 회원탈퇴기능은 verify로 deleteUser 메소드가 호출되었는지 확인하는 방법 말고는 존재하지 않는다.
     *          만약 컬럼값 1개만 Y->N 으로 변경했더라면 객체간 비교를 해줄 수 있지만, 아에 테이블에서 삭제를 해버리는 이상 마땅한 방법이 없음
     */
    @DeleteMapping("/profile")
    public ResponseEntity<?> deleteUser(@SessionAttribute(value = LOGIN_MEMBER,required = false) SessionUser sessionDTO){

        String id = sessionDTO.getUserId();

        userService.deleteUser(id);
        return RESPONSE_OK;
    }
}
