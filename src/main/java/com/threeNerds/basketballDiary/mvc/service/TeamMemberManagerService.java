package com.threeNerds.basketballDiary.mvc.service;

import com.threeNerds.basketballDiary.mvc.domain.JoinRequestStateCode;
import com.threeNerds.basketballDiary.mvc.domain.JoinRequestTypeCode;
import com.threeNerds.basketballDiary.mvc.domain.TeamJoinRequest;
import com.threeNerds.basketballDiary.mvc.repository.TeamJoinRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 팀관리자가 팀원을 관리하기 위한 업무를 수행하는 Service
 * @author 책임자 작성
 *
 * issue and history
 * <pre>
 * 2022.02.08 여인준 : 소스코드 생성
 * </pre>
 */

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class TeamMemberManagerService {

    private final TeamJoinRequestRepository teamJoinRequestRepository;

    // 팀원 초대 API
    public void inviteTeamMember(TeamJoinRequest teamJoinRequest) {
        TeamJoinRequest invitationInfo = TeamJoinRequest.builder()
                .teamSeq(teamJoinRequest.getTeamSeq())
                .userSeq(teamJoinRequest.getUserSeq())
                .joinRequestTypeCode(JoinRequestTypeCode.INVITATION.getCode())
                .joinRequestStateCode(JoinRequestStateCode.WAITING.getCode())
                .build();

        // 초대-가입요청 존재여부 확인 : 대기중인 가입요청 혹은 초대가 있을 경우 중복가입요청 방지
        TeamJoinRequest prevJoinReq = null; // = teamJoinRequestRepository.checkJoinRequest(invitationInfo);
        boolean isExistJoinRequest = prevJoinReq != null
                                        ? true
                                        : false;
        if (isExistJoinRequest)
        {
            log.debug("이미 {}이 있습니다.", JoinRequestTypeCode.valueOf(prevJoinReq.getJoinRequestTypeCode()));
            return; // TODO 에러를 던지거나 다른 것을 리턴하기
        }
        teamJoinRequestRepository.createJoinRequest(invitationInfo);
    }



}
