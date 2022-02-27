package com.threeNerds.basketballDiary.mvc.service;

import com.threeNerds.basketballDiary.constant.*;
import com.threeNerds.basketballDiary.mvc.domain.Team;
import com.threeNerds.basketballDiary.mvc.domain.TeamJoinRequest;
import com.threeNerds.basketballDiary.mvc.domain.TeamMember;
import com.threeNerds.basketballDiary.mvc.dto.JoinRequestDTO;
import com.threeNerds.basketballDiary.mvc.dto.KeyDTO;
import com.threeNerds.basketballDiary.mvc.dto.PlayerDTO;
import com.threeNerds.basketballDiary.mvc.dto.PlayerSearchDTO;
import com.threeNerds.basketballDiary.mvc.repository.PlayerRepository;
import com.threeNerds.basketballDiary.mvc.repository.TeamJoinRequestRepository;
import com.threeNerds.basketballDiary.mvc.repository.TeamMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * 팀관리자가 팀원을 관리하기 위한 업무를 수행하는 Service
 * @author 책임자 작성
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
@Transactional // TODO 공부할 거리 - AOP적용한 어노테이션?!!!
public class TeamMemberManagerService {

    private final TeamJoinRequestRepository teamJoinRequestRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final PlayerRepository playerRepository;

    /**
     * 팀원 초대 API
     * @param joinRequest
     */
    public void inviteTeamMember(JoinRequestDTO joinRequest) {
        Long teamSeq = joinRequest.getTeamSeq();
        Long userSeq = joinRequest.getUserSeq();

        TeamJoinRequest invitationInfo = TeamJoinRequest.builder()
                .teamSeq(teamSeq)
                .userSeq(userSeq)
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
        Long teamSeq = joinRequest.getTeamSeq();
        Long userSeq = joinRequest.getUserSeq();

        /** 팀원 추가 - 예외처리 필요 */
        String currentYmd = LocalDate.now().toString().replace("-", "");
        TeamMember teamMemberInfo = new TeamMember().builder()
                .teamSeq(teamSeq)
                .userSeq(userSeq)
                .teamAuthCode(TeamAuthCode.TEAM_MEMBER.getCode())
                .joinYmd(currentYmd)
                .withdrawalYn("Y").build();
        // TeamMember 테이블에 데이터 넣기
        // TODO 중복된 가입요청 방지필요 (소속팀에 가입요청 혹은 초대가 없어야 하고, 들어왔다고 해도 테이블에 2건 이상이 추가 되지 않도록 막아야 함)
        boolean isExistTeamMember = teamMemberRepository.checkTeamMember(teamMemberInfo) > 0 ? true : false;
        if (isExistTeamMember) {
            log.info("해당 선수는 이미 팀원으로 등록되어 있습니다.");
            return false; // TODO 오류 던지기
        }
        teamMemberRepository.saveTeamMemeber(teamMemberInfo);

        /** 가입요청 상태 업데이트 하기 */
        TeamJoinRequest approvalInfo = TeamJoinRequest.builder()
                .teamJoinRequestSeq(joinRequest.getTeamJoinRequestSeq())
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

        players.stream().forEach(player -> {
                player.positionCodeName(PositionCode.getName(player.getPositionCode()))
                      .joinRequestStateCodeName(JoinRequestStateCode.getName(player.getJoinRequestStateCode()));
        });

        return players;
    }

    /**
     * 소속팀에 가입요청한 선수목록 조회 API
     * @param searchCond
     * @return List<PlayerDTO>
     */
    public List<PlayerDTO> searchJoinRequestPlayer(PlayerSearchDTO searchCond) {
        searchCond.joinRequestTypeCode(JoinRequestTypeCode.JOIN_REQUEST.getCode());
        List<PlayerDTO> players = playerRepository.findPlayers(searchCond);

        players.stream().forEach(player -> { player
                                                .positionCodeName(PositionCode.getName(player.getPositionCode()))
                                                .joinRequestStateCodeName(JoinRequestStateCode.getName(player.getJoinRequestStateCode()));
        });

        return players;
    }

    /**
     * 소속팀 회원 강퇴시키기
     * @param teamMemberKey
     * @return List<PlayerDTO>
     */
    public void removeTeamMember(KeyDTO.TeamMember teamMemberKey)
    {
        TeamMember teamMember = TeamMember.builder()
                .teamMemberSeq(teamMemberKey.getTeamMemberSeq())
                .teamSeq(teamMemberKey.getTeamSeq())
                .withdrawalYn(State.Withdrawal.Y)
                .build();
        // TODO 팀멤버 테이블에 있는 정보를 건들지 않고 퇴장여부 상태값만 바꾸는 것으로 할지 판단 필요
        teamMemberRepository.updateWithdrawalState(teamMember);

    }

    /**
     * 소속팀 관리자 임명하기
     * @param teamMemberKey
     * @return
     */
    public void appointManager(KeyDTO.TeamMember teamMemberKey)
    {
        TeamMember teamMember = TeamMember.builder()
                .teamMemberSeq(teamMemberKey.getTeamMemberSeq())
                .teamSeq(teamMemberKey.getTeamSeq())
                .teamAuthCode(TeamAuthCode.MANGER.getCode())
                .build();

        boolean isSuccess = teamMemberRepository.updateTeamAuth(teamMember) == 1 ? true : false;
        if (!isSuccess)
        {
            log.info("===== 팀원을 찾을 수 없습니다. =====");
            return; //TODO 예외처리해서 Exception으로 처리하기
        }
    }

    /**
     * 소속팀 관리자 임명하기
     * @param teamMemberKeys
     * @return
     */
    public void dismissManager(KeyDTO.TeamMember teamMemberKeys)
    {
        TeamMember teamMember = TeamMember.builder()
                .teamMemberSeq(teamMemberKeys.getTeamMemberSeq())
                .teamSeq(teamMemberKeys.getTeamSeq())
                .teamAuthCode(TeamAuthCode.TEAM_MEMBER.getCode())
                .build();

        boolean isSuccess = teamMemberRepository.updateTeamAuth(teamMember) == 1 ? true : false;
        if (!isSuccess)
        {
            log.info("===== 팀원을 찾을 수 없습니다. =====");
            return; //TODO 예외처리해서 Exception으로 처리하기
        }
    }
}
