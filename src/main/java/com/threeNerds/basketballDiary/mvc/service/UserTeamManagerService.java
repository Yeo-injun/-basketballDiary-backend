package com.threeNerds.basketballDiary.mvc.service;

import com.threeNerds.basketballDiary.constant.JoinRequestTypeCode;
import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.mvc.domain.TeamJoinRequest;
import com.threeNerds.basketballDiary.mvc.domain.TeamMember;
import com.threeNerds.basketballDiary.mvc.domain.User;
import com.threeNerds.basketballDiary.mvc.dto.AuthUserRequestDTO;
import com.threeNerds.basketballDiary.mvc.dto.loginUser.userTeamManager.JoinRequestDTO;
import com.threeNerds.basketballDiary.mvc.dto.loginUser.CmnLoginUserDTO;
import com.threeNerds.basketballDiary.mvc.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.threeNerds.basketballDiary.exception.Error.*;

/**
 * 사용자가 팀의 구성원으로서 관련된 업무를 수행하기 위한 Service
 * @author 여인준
 *
 * issue and history
 * <pre>
 * 2022.02.08 여인준 : 소스코드 생성
 * 2022.02.22 여인준 : CodeEnum에서 Code로 Name가져오는 메소드 구현 및 적용 완료
 * </pre>
 */

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserTeamManagerService {

    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final TeamJoinRequestRepository teamJoinRequestRepository;
    private final UserTeamManagerRepository userTeamManagerRepository;
    private final UserRepository userRepository;

    // 농구팀에 가입요청 보내기
    public void sendJoinRequestToTeam(CmnLoginUserDTO loginUserDTO)
    {
        TeamJoinRequest joinRequestInfo = TeamJoinRequest.createJoinRequest(loginUserDTO);
        /** 초대-가입요청 존재여부 확인 : 대기중인 가입요청 혹은 초대가 있을 경우 중복가입요청 방지 */
        boolean isExistPrevJoinRequest = teamJoinRequestRepository.checkPendingJoinRequest(joinRequestInfo) == 1 ? true : false;
        if (isExistPrevJoinRequest)
        {
            throw new CustomException(ALREADY_EXIST_JOIN_REQUEST);
        }

        /** 팀 존재여부 확인 */
        Optional
            .ofNullable(teamRepository.findByTeamSeq(joinRequestInfo.getTeamSeq()))
            .orElseThrow(()-> new CustomException(TEAM_NOT_FOUND));

        /** 팀이 존재하고, 초대-가입요청이 없을경우에만 INSERT */
        teamJoinRequestRepository.createJoinRequest(joinRequestInfo);
    }

    // 사용자가 가입요청을 보낸 팀 목록을 조회
    public List<JoinRequestDTO> getJoinRequestsTo(CmnLoginUserDTO loginUserDTO)
    {
        JoinRequestDTO joinRequestDTO = new JoinRequestDTO()
                .userSeq(loginUserDTO.getUserSeq())
                .joinRequestTypeCode(JoinRequestTypeCode.JOIN_REQUEST.getCode());

        List<JoinRequestDTO> joinRequestDTOList = userTeamManagerRepository.findJoinRequestsByType(joinRequestDTO);
        for (JoinRequestDTO joinRequest : joinRequestDTOList)
        {
            joinRequest.setCodeNameByInstanceCodeValue();
        }
        return joinRequestDTOList;
    }

    // 사용자가 초대 받은 팀 목록을 조회
    public List<JoinRequestDTO> getJoinRequestsFrom(CmnLoginUserDTO loginUserDTO)
    {
        JoinRequestDTO joinRequestDTO = new JoinRequestDTO()
                .userSeq(loginUserDTO.getUserSeq())
                .joinRequestTypeCode(JoinRequestTypeCode.INVITATION.getCode());

        List<JoinRequestDTO> joinRequestDTOList = userTeamManagerRepository.findJoinRequestsByType(joinRequestDTO);
        for (JoinRequestDTO joinRequest : joinRequestDTOList)
        {
            joinRequest.setCodeNameByInstanceCodeValue();
        }
        return joinRequestDTOList;
    }

    // 사용자가 가입요청한 건을 취소상태로 바꾸는 API (데이터를 삭제하지는 않음)
    public void cancelJoinRequest(CmnLoginUserDTO loginUserDTO)
    {
        TeamJoinRequest joinRequestCancel = TeamJoinRequest.cancelJoinRequest(loginUserDTO);

       boolean isCancelSuccess = teamJoinRequestRepository.updateJoinRequestState(joinRequestCancel) == 1 ? true : false;
        if (!isCancelSuccess)
        {
            throw new CustomException(JOIN_REQUEST_NOT_FOUND);
        }
    }

    // 사용자가 팀의 초대를 승인하는 API
    public Map<Long, Long> approveInvitation(CmnLoginUserDTO loginUserDTO)
    {
        /** 초대요청 상태 업데이트 하기 */
        boolean isSuccess = teamJoinRequestRepository.updateJoinRequestState(TeamJoinRequest.approveInvitation(loginUserDTO)) == 1 ? true : false;
        if (!isSuccess) {
            throw new CustomException(JOIN_REQUEST_NOT_FOUND);
        }

        /** 팀원 추가 */
        TeamJoinRequest joinInfo = teamJoinRequestRepository.findUserByTeamJoinRequestSeq(loginUserDTO.getTeamJoinRequestSeq());
        TeamMember newTeamMember = TeamMember.createNewMember(joinInfo);
        teamMemberRepository.saveTeamMemeber(newTeamMember);

        /** 새로운 권한정보 생성 및 return */
        User user = new User().builder()
                        .userSeq(loginUserDTO.getUserSeq())
                        .build();

        /** TODO 세션에 권한 세팅하는 공통함수를 설정해야 함 - 임원 제명 선정 등의 API에서 활용 */
        List<AuthUserRequestDTO> userAuthList = userRepository.findAuthList(user);
        Map<Long, Long> userAuth = userAuthList.stream()
                .collect(Collectors.toMap(authDTO -> Long.parseLong(authDTO.getTeamSeq()),
                        authDTO -> Long.parseLong(authDTO.getTeamAuthCode())));
        return userAuth;
    }

    // 팀 초대 거절 API
    public void rejectInvitation(CmnLoginUserDTO loginUserDTO)
    {
        TeamJoinRequest rejectInvitation = TeamJoinRequest.rejectInvitation(loginUserDTO);

        boolean isSussess = teamJoinRequestRepository.updateJoinRequestState(rejectInvitation) == 1 ? true : false;
        if (!isSussess)
        {
            throw new CustomException(INVITATION_NOT_FOUND);
        }

    }
}
