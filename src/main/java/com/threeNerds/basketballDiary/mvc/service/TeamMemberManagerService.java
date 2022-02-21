package com.threeNerds.basketballDiary.mvc.service;

import com.threeNerds.basketballDiary.constant.JoinRequestStateCode;
import com.threeNerds.basketballDiary.constant.JoinRequestTypeCode;
import com.threeNerds.basketballDiary.constant.PositionCode;
import com.threeNerds.basketballDiary.mvc.domain.TeamJoinRequest;
import com.threeNerds.basketballDiary.mvc.dto.JoinRequestDTO;
import com.threeNerds.basketballDiary.mvc.dto.PlayerDTO;
import com.threeNerds.basketballDiary.mvc.dto.PlayerSearchDTO;
import com.threeNerds.basketballDiary.mvc.repository.PlayerRepository;
import com.threeNerds.basketballDiary.mvc.repository.TeamJoinRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    private final PlayerRepository playerRepository;

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
            String reqTypeName = JoinRequestTypeCode.getName(prevJoinReq.getJoinRequestTypeCode());
            log.info("==== 이미 {}가 있습니다. ====", reqTypeName);
            return; // TODO 에러를 던지거나 다른 것을 리턴하기
        }

        // 초대-가입요청이 없을경우에만 INSERT
        teamJoinRequestRepository.createJoinRequest(invitationInfo);
    }

    /**
     * 소속팀 가입요청 승인 API
     * @param joinRequest
     */
    public boolean approveJoinRequest(JoinRequestDTO joinRequest) {
        TeamJoinRequest approvalInfo = TeamJoinRequest.builder()
                .teamJoinRequestSeq(joinRequest.getTeamJoinRequestSeq())
                .teamSeq(joinRequest.getTeamSeq())
                .joinRequestStateCode(JoinRequestStateCode.APPROVAL.getCode())
                .build();

        boolean isApprovalSuccess = teamJoinRequestRepository.updateJoinRequestState(approvalInfo) == 1 ? true : false;
        if (!isApprovalSuccess)
        {
            log.info("==== 해당 가입요청은 승인할 수 없는 가입요청입니다. ====");
            return isApprovalSuccess; // TODO 에러를 던지는 것으로 코드 바꾸기
        }

        return isApprovalSuccess;
    }

    /**
     * 소속팀 가입요청 거절 API
     * @param joinRequest
     */
    public boolean rejectJoinRequest(JoinRequestDTO joinRequest) {
        TeamJoinRequest rejectionInfo = TeamJoinRequest.builder()
                .teamJoinRequestSeq(joinRequest.getTeamJoinRequestSeq())
                .teamSeq(joinRequest.getTeamSeq())
                .joinRequestStateCode(JoinRequestStateCode.REJECTION.getCode())
                .build();

        boolean isRejectionSuccess = teamJoinRequestRepository.updateJoinRequestState(rejectionInfo) == 1 ? true : false;
        if (!isRejectionSuccess)
        {
            log.info("==== 해당 가입요청은 거절할 수 없는 가입요청입니다. ====");
            return isRejectionSuccess; // TODO 에러를 던지는 것으로 코드 바꾸기
        }
        return isRejectionSuccess;
    }

    /**
     * 소속팀에서 초대한 선수목록 조회 API
     * @param searchCond
     * @return List<PlayerDTO>
     */
    public List<PlayerDTO> searchInvitedPlayer(PlayerSearchDTO searchCond) {
        searchCond.joinRequestTypeCode(JoinRequestTypeCode.INVITATION.getCode());
        List<PlayerDTO> players = playerRepository.findPlayers(searchCond);

        // TODO 스트림으로 처리 요망.. 코드값을 가지고 코드명칭으로 바꿔주기
        players.stream().forEach(player -> {
                player.positionCodeName(PositionCode.getName(player.getPositionCode()))
                      .joinRequestStateCodeName(JoinRequestStateCode.getName(player.getJoinRequestStateCode()));
        });

        return players;
    }

}
