package com.threeNerds.basketballDiary.mvc.service;

import com.threeNerds.basketballDiary.constant.JoinRequestStateCode;
import com.threeNerds.basketballDiary.constant.JoinRequestTypeCode;
import com.threeNerds.basketballDiary.mvc.domain.TeamJoinRequest;
import com.threeNerds.basketballDiary.mvc.dto.JoinRequestDTO;
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

    /**
     * 팀원 초대 API
     * @param joinRequest
     */
    public void inviteTeamMember(JoinRequestDTO joinRequest) {
        TeamJoinRequest invitationInfo = TeamJoinRequest.builder()
                .teamSeq(joinRequest.getTeamSeq())
                .userSeq(joinRequest.getUserSeq())
                .joinRequestTypeCode(JoinRequestTypeCode.INVITATION.getCode())
                .joinRequestStateCode(JoinRequestStateCode.WAITING.getCode())
                .build();

        // 초대-가입요청 존재여부 확인 : 대기중인 가입요청 혹은 초대가 있을 경우 중복가입요청 방지
        TeamJoinRequest prevJoinReq = teamJoinRequestRepository.checkJoinRequest(invitationInfo);
        boolean isExistJoinRequest = prevJoinReq != null
                                        ? true
                                        : false;
        if (isExistJoinRequest)
        {
            String reqTypeName = getJoinRequestTypeName(prevJoinReq);
            log.info("==== 이미 {}가 있습니다. ====", reqTypeName);
            return; // TODO 에러를 던지거나 다른 것을 리턴하기
        }

        // 초대-가입요청이 없을경우에만 INSERT
        teamJoinRequestRepository.createJoinRequest(invitationInfo);
    }

    private String getJoinRequestTypeName(TeamJoinRequest prevJoinReq) {
        String reqTypeCode = prevJoinReq.getJoinRequestTypeCode();
        String reqTypeName = "";

        // TODO Java8 문법 적용해서 Stream으로 처리 요망
        JoinRequestTypeCode[] reqTypeEnum = JoinRequestTypeCode.values();
        for (JoinRequestTypeCode reqType : reqTypeEnum)
        {
            if (reqType.getCode().equals(reqTypeCode))
            {
                reqTypeName = reqType.getName();
            }
        }
        return reqTypeName;
    }


    /**
     * 소속팀 가입요청 승인 API
     * @param joinRequest
     */
    public String approveJoinRequest(JoinRequestDTO joinRequest) {
        TeamJoinRequest approvalInfo = TeamJoinRequest.builder()
                .teamJoinRequestSeq(joinRequest.getTeamJoinRequestSeq())
                .teamSeq(joinRequest.getTeamSeq())
                .joinRequestStateCode(JoinRequestStateCode.APPROVAL.getCode())
                .build();

        teamJoinRequestRepository.updateJoinRequestState(approvalInfo);
        return "";
    }
}
