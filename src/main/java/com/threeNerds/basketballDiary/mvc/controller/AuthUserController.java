package com.threeNerds.basketballDiary.mvc.controller;

import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.interceptor.Auth;
import com.threeNerds.basketballDiary.mvc.domain.User;
import com.threeNerds.basketballDiary.mvc.dto.loginUser.CmnLoginUserDTO;
import com.threeNerds.basketballDiary.mvc.dto.loginUser.userTeamManager.JoinRequestDTO;
import com.threeNerds.basketballDiary.mvc.dto.loginUser.PasswordDTO;
import com.threeNerds.basketballDiary.mvc.dto.user.user.UpdateUserDTO;
import com.threeNerds.basketballDiary.mvc.dto.user.user.UserDTO;
import com.threeNerds.basketballDiary.mvc.service.UserService;
import com.threeNerds.basketballDiary.mvc.service.UserTeamManagerService;
import com.threeNerds.basketballDiary.session.SessionUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static com.threeNerds.basketballDiary.constant.Constant.USER;
import static com.threeNerds.basketballDiary.exception.Error.INCORRECT_PASSWORD;
import static com.threeNerds.basketballDiary.utils.HttpResponses.RESPONSE_CREATED;
import static com.threeNerds.basketballDiary.utils.HttpResponses.RESPONSE_OK;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/loginUser")
public class AuthUserController {

    private final UserService userService;
    private final UserTeamManagerService userTeamManagerService;

    /**
     *  API020 : 농구팀 가입요청 보내기
     *  22.03.25 인준 : SessionUser null체크후 예외처리 적용. Service Layer에서의 예외처리 적용
     *  22.03.26 인준 : SessionUser null체크 로직 제거 - 인터셉터에서 하기 때문.
     *  22.03.29 인준 : 권한어노테이션 추가
     **/
    @Auth(GRADE = USER)
    @PostMapping("/joinRequestTo/{teamSeq}")
    public ResponseEntity<?> sendJoinRequestToTeam(
            @SessionAttribute(value = "loginUser", required = false) SessionUser sessionUser,
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
     *  22.03.26 인준 : SessionUser null체크 로직 제거 - 인터셉터에서 체크하기 때문
     *  22.03.29 인준 : 권한어노테이션 추가
     **/
    @Auth(GRADE = USER)
    @GetMapping("/joinRequestsTo")
    public ResponseEntity<?> getJoinRequestsTo (
            @SessionAttribute(value = "loginUser", required = false) SessionUser sessionUser
    ) {
        Long userSeq = sessionUser.getUserSeq();
        CmnLoginUserDTO loginUserDTO = new CmnLoginUserDTO()
                .userSeq(userSeq);

        List<JoinRequestDTO> result = userTeamManagerService.getJoinRequestsTo(loginUserDTO);
        // TODO ResponseDTO로 감싸서 보내주기 ResponseDTO를 조회용 DTO의 공통부분을 추상화(페이징, 목록의 갯수 등)하고 이를 상속받아서
        // 매 조회요청 Controller의 메소드이름DTO로 만들기 
        return ResponseEntity.ok().body(result);
    }

    /**
     *  API023 : 팀 가입요청 취소
     *  22.03.29 인준 : 권한어노테이션 추가
     **/
    @Auth(GRADE = USER)
    @DeleteMapping("/joinRequestsTo/{teamJoinRequestSeq}")
    public ResponseEntity<?> cancelJoinReqeust (
            @SessionAttribute(value = "loginUser", required = false) SessionUser sessionUser,
            @PathVariable Long teamJoinRequestSeq
    ) {
        CmnLoginUserDTO loginUserDTO = new CmnLoginUserDTO()
                .teamJoinRequestSeq(teamJoinRequestSeq)
                .userSeq(sessionUser.getUserSeq());

        userTeamManagerService.cancelJoinRequest(loginUserDTO);
        // TODO 같은 서비스에서 호출해야 하는 것인지 아니면 컨트롤러에서 별도로 호출해야 하는것인지..
        // 트랜잭션 관리를 어떻게 할 것인지가 관건으로 판단됨.
        List<JoinRequestDTO> joinRequestDTOList = userTeamManagerService.getJoinRequestsTo(loginUserDTO);
        return ResponseEntity.ok().body(joinRequestDTOList);
    }

    /**
     *  API024 : 팀 초대 승인
     *  22.03.29 인준 : 권한어노테이션 추가
     **/
    @Auth(GRADE = USER)
    @PutMapping("/joinRequestsFrom/{teamJoinRequestSeq}/approval")
    public ResponseEntity<?> approveInvitation (
            @SessionAttribute(value = "loginUser", required = false) SessionUser sessionUser,
            @PathVariable Long teamJoinRequestSeq
    ) {
        Long userSeq = sessionUser.getUserSeq();
        CmnLoginUserDTO loginUserDTO = new CmnLoginUserDTO()
                .teamJoinRequestSeq(teamJoinRequestSeq)
                .userSeq(userSeq);

        Map<Long, Long> userAuth = userTeamManagerService.approveInvitation(loginUserDTO);

        /** 세션 정보 update */
        sessionUser.setUserAuth(userAuth);

        // TODO 컨트롤러에서 서비스 호출하는 방식을 허용할 것인지 -> 우선 트랜잭션 이슈 검토, 서비스레이어의 역할 및 책임에 대해서 다시 공부 검토
        List<JoinRequestDTO> joinRequestDTOList = userTeamManagerService.getJoinRequestsFrom(loginUserDTO);
        return ResponseEntity.ok().body(joinRequestDTOList);
    }

    /**
     *  API032 : 농구팀 초대 목록 조회
     *  22.03.13 인준 : API022 세분화 - 가입요청 및 초대 목록을 하나의 API콜로 가져오는 것에서 API 2개를 콜해서 가져오는 구조로 변경
     *  22.03.29 인준 : 권한어노테이션 추가
     **/
    @Auth(GRADE = USER)
    @GetMapping("/joinRequestsFrom")
    public ResponseEntity<?> getJoinRequestsFrom(
            @SessionAttribute(value = "loginUser", required = false) SessionUser sessionUser
    ) {
        Long userSeq = sessionUser.getUserSeq();
        CmnLoginUserDTO loginUserDTO = new CmnLoginUserDTO()
                .userSeq(userSeq);

        List<JoinRequestDTO> result = userTeamManagerService.getJoinRequestsFrom(loginUserDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     *  API033 : 농구팀 초대 거절
     *  22.03.29 인준 : 권한어노테이션 추가
     **/
    @Auth(GRADE = USER)
    @PutMapping("/joinRequestsFrom/{teamJoinRequestSeq}/rejection")
    public ResponseEntity<?> rejectInvitation (
            @SessionAttribute(value="loginUser", required = false) SessionUser sessionUser,
            @PathVariable Long teamJoinRequestSeq
    ) {
        CmnLoginUserDTO loginUserDTO = new CmnLoginUserDTO()
                .teamJoinRequestSeq(teamJoinRequestSeq)
                .userSeq(sessionUser.getUserSeq());

        userTeamManagerService.rejectInvitation(loginUserDTO);
        // TODO 컨트롤러에서 서비스 호출하는 방식을 허용할 것인지 -> 우선 트랜잭션 이슈 검토, 서비스레이어의 역할 및 책임에 대해서 다시 공부 검토
        List<JoinRequestDTO> joinRequestDTOList = userTeamManagerService.getJoinRequestsFrom(loginUserDTO);
        return ResponseEntity.ok().body(joinRequestDTOList);
    }

    /**끝 인준 API **************************************************************************************************************/

    /**
     * API025 회원정보 수정데이터 조회
     */
    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getMyInfo(
            @SessionAttribute(value = "loginUser", required = false) SessionUser sessionDTO
    ){

        Long id = sessionDTO.getUserSeq();
        User user = userService.findUser(id);
        UserDTO userDto = UserDTO.getUserDto(user);
        return ResponseEntity.ok().body(userDto);
    }



    /**
     * API026 회원수정 : update 를 수행한 후 update 된 객체를 리턴시켜주자 => 이래야 TEST CODE 작성시 정확히 update 가 되었는지 확인할 수 있다.
     */
    @PostMapping("/profile")
    public ResponseEntity<?> updateUser(
            @SessionAttribute(value = "loginUser",required = false) SessionUser sessionDTO,
            @RequestBody @Valid UpdateUserDTO userDTO
    ) {
        userService.updateUser(userDTO.userSeq(sessionDTO.getUserSeq()));

        return ResponseEntity.ok().body(userDTO);
    }

    /**
     * API028 회원탈퇴 : 회원탈퇴기능은 verify로 deleteUser 메소드가 호출되었는지 확인하는 방법 말고는 존재하지 않는다.
     *          만약 컬럼값 1개만 Y->N 으로 변경했더라면 객체간 비교를 해줄 수 있지만, 아에 테이블에서 삭제를 해버리는 이상 마땅한 방법이 없음
     */
    @DeleteMapping("/profile")
    public ResponseEntity<?> deleteUser(
            @SessionAttribute(value = "loginUser",required = false) SessionUser sessionDTO
    ){

        String id = sessionDTO.getUserId();

        userService.deleteUser(id);
        return RESPONSE_OK;
    }

    /**
     * API027 비밀번호 변경
     */
    @PostMapping("/profile/password")
    public ResponseEntity<?> updatePassword(
            @SessionAttribute(value = "loginUser",required = false) SessionUser sessionDTO,
            @RequestBody PasswordDTO passwordDTO
    ){
        User user = userService.findUser(sessionDTO.getUserSeq());

        if(!user.getPassword().equals(passwordDTO.getPrevPassword())) {
            throw new CustomException(INCORRECT_PASSWORD);
        }

        userService.updatePassword(PasswordDTO.retPasswordDto(passwordDTO,user.getUserSeq()));
        return RESPONSE_OK;
    }
}