package com.threeNerds.basketballDiary.mvc.service;

import com.threeNerds.basketballDiary.constant.*;
import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.Error;
import com.threeNerds.basketballDiary.mvc.domain.TeamJoinRequest;
import com.threeNerds.basketballDiary.mvc.domain.TeamMember;
import com.threeNerds.basketballDiary.mvc.dto.loginUser.userTeamManager.JoinRequestDTO;
import com.threeNerds.basketballDiary.mvc.dto.PlayerDTO;
import com.threeNerds.basketballDiary.mvc.dto.PlayerSearchDTO;
import com.threeNerds.basketballDiary.mvc.dto.myTeam.CmnMyTeamDTO;
import com.threeNerds.basketballDiary.mvc.repository.PlayerRepository;
import com.threeNerds.basketballDiary.mvc.repository.TeamJoinRequestRepository;
import com.threeNerds.basketballDiary.mvc.repository.TeamMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.threeNerds.basketballDiary.exception.Error.USER_NOT_FOUND;

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
    public void inviteTeamMember(CmnMyTeamDTO joinRequest)
    {
        joinRequest.joinRequestTypeCode(JoinRequestTypeCode.INVITATION.getCode());
        TeamJoinRequest invitationInfo = TeamJoinRequest.createInvitation(joinRequest);

        /** 초대-가입요청 존재여부 확인 : 대기중인 가입요청 혹은 초대가 있을 경우 중복가입요청 방지 */
        int pendingJoinReqCnt = teamJoinRequestRepository.checkPendingJoinRequest(invitationInfo);
        if (pendingJoinReqCnt > 0)
        {
            throw new CustomException(Error.ALREADY_EXIST_JOIN_REQUEST);
        }

        /** 팀원으로 존재하는지 확인 : 팀원으로 존재할 경우 예외를 던짐(409에러) */
        int duplicatedTeamMemberCnt = teamMemberRepository.checkDuplicatedTeamMember(joinRequest);
        if (duplicatedTeamMemberCnt > 0)
        {
            throw new CustomException(Error.ALREADY_EXIST_TEAM_MEMBER);
        }

        /** 초대-가입요청이 없고, 팀원이 아닐 경우에만 INSERT */
        teamJoinRequestRepository.createJoinRequest(invitationInfo);
    }

    /**
     * 소속팀 가입요청 승인 API
     * @param joinRequest
     */
    public void approveJoinRequest(CmnMyTeamDTO joinRequest)
    {
        /** 가입요청 상태 업데이트 하기 */
        boolean isApproveSuccess = teamJoinRequestRepository
                                        .updateJoinRequestState(TeamJoinRequest.approveJoinRequest(joinRequest)) == 1 ? true : false;
        if (!isApproveSuccess)
        {
            throw new CustomException(USER_NOT_FOUND);
        }

        /** 팀원 추가 */
        TeamJoinRequest joinRequestInfo = teamJoinRequestRepository.findUserByTeamJoinRequestSeq(joinRequest.getTeamJoinRequestSeq());
        TeamMember newTeamMember = TeamMember.create(joinRequestInfo);
        teamMemberRepository.saveTeamMemeber(newTeamMember);
    }

    /**
     * 소속팀 가입요청 거절 API
     * @param joinRequest
     */
    public void rejectJoinRequest(CmnMyTeamDTO joinRequest)
    {
        TeamJoinRequest rejectionInfo = TeamJoinRequest.rejectJoinRequest(joinRequest);

        boolean isRejectionSuccess = teamJoinRequestRepository
                                        .updateJoinRequestState(rejectionInfo) == 1 ? true : false;
        if (!isRejectionSuccess)
        {
            throw new CustomException(Error.JOIN_REQUEST_NOT_FOUND);
        }
    }

    /**
     * 소속팀에서 초대한 선수목록 조회 API
     * @param playerSearchCond
     * @return List<PlayerDTO>
     */
    public List<PlayerDTO> searchInvitedPlayer(CmnMyTeamDTO playerSearchCond)
    {
        playerSearchCond.joinRequestTypeCode(JoinRequestTypeCode.INVITATION.getCode());
        List<PlayerDTO> players = playerRepository.findPlayers(playerSearchCond);

        players.stream() // TODO Stream에서는 빈 List를 어떻게 처리하는지 확인할 필요가 있음.
                .forEach(player -> {
                                player.positionCodeName(PositionCode.getName(player.getPositionCode()))
                                      .joinRequestStateCodeName(JoinRequestStateCode.getName(player.getJoinRequestStateCode()));
        });

        return players;
    }

    /**
     * 소속팀에 가입요청한 선수목록 조회 API
     * @param playerSearchCond
     * @return List<PlayerDTO>
     */
    public List<PlayerDTO> searchJoinRequestPlayer(CmnMyTeamDTO playerSearchCond) {
        playerSearchCond.joinRequestTypeCode(JoinRequestTypeCode.JOIN_REQUEST.getCode());
        List<PlayerDTO> players = playerRepository.findPlayers(playerSearchCond);

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
    public void removeTeamMember(CmnMyTeamDTO teamMemberKey)
    {
        TeamMember teamMember = TeamMember.withdrawalMember(teamMemberKey);
        boolean isWithdrawal = teamMemberRepository.updateWithdrawalState(teamMember) == 1 ? true : false;
        if (!isWithdrawal)
        {
            throw new CustomException(USER_NOT_FOUND);
        }
    }

    /**
     * 소속팀 관리자 임명하기
     * @param teamMemberKey
     */
    public void appointManager(CmnMyTeamDTO teamMemberKey) {
        // TODO key로 도메인 객체를 조회해서 도메인 객체의 메소드로 작업하기
        TeamMember memberToManager = TeamMember.toManager(teamMemberKey);

        boolean isSuccess = teamMemberRepository.updateTeamAuth(memberToManager) == 1;
        if (!isSuccess) {
            throw new CustomException(USER_NOT_FOUND);
        }
    }

    /**
     * 소속팀 관리자 해임하기
     * @param teamMemberKeys
     */
    public void dismissManager(CmnMyTeamDTO teamMemberKeys)
    {
        TeamMember manager = teamMemberRepository.findByTeamMemberSeq(teamMemberKeys.getTeamMemberSeq());
        TeamMember managerToMember = manager.toMember();

        boolean isSuccess = teamMemberRepository.updateTeamAuth(managerToMember) == 1;
        if (!isSuccess) {
            throw new CustomException(USER_NOT_FOUND);
        }
    }
}
