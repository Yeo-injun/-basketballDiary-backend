package com.threeNerds.basketballDiary.mvc.myTeam.service;

import com.threeNerds.basketballDiary.constant.code.type.JoinRequestStateCode;
import com.threeNerds.basketballDiary.constant.code.type.JoinRequestTypeCode;
import com.threeNerds.basketballDiary.constant.code.type.PositionCode;
import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.error.DomainErrorType;
import com.threeNerds.basketballDiary.mvc.myTeam.domain.TeamJoinRequest;
import com.threeNerds.basketballDiary.mvc.myTeam.domain.TeamMember;
import com.threeNerds.basketballDiary.mvc.myTeam.service.dto.TeamAuthCommand;
import com.threeNerds.basketballDiary.mvc.team.dto.PlayerDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.CmnMyTeamDTO;
import com.threeNerds.basketballDiary.mvc.team.repository.dto.PlayerRepository;
import com.threeNerds.basketballDiary.mvc.myTeam.repository.TeamJoinRequestRepository;
import com.threeNerds.basketballDiary.mvc.myTeam.repository.TeamMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.threeNerds.basketballDiary.exception.error.DomainErrorType.NOT_FOUND_TEAM_INFO;
import static com.threeNerds.basketballDiary.exception.error.DomainErrorType.USER_NOT_FOUND;

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
@Transactional
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
        if (pendingJoinReqCnt > 0) {
            throw new CustomException(DomainErrorType.ALREADY_EXIST_JOIN_REQUEST);
        }

        /** 팀원으로 존재하는지 확인 : 팀원으로 존재할 경우 예외를 던짐(409에러) */
        int duplicatedTeamMemberCnt = teamMemberRepository.checkDuplicatedTeamMember(joinRequest);
        if (duplicatedTeamMemberCnt > 0) {
            throw new CustomException(DomainErrorType.ALREADY_EXIST_TEAM_MEMBER);
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
        if (!isApproveSuccess) {
            throw new CustomException(USER_NOT_FOUND);
        }

        /** 팀원 추가 */
        TeamJoinRequest joinRequestInfo = teamJoinRequestRepository.findUserByTeamJoinRequestSeq(joinRequest.getTeamJoinRequestSeq());
        TeamMember newTeamMember = TeamMember.create(joinRequestInfo);
        teamMemberRepository.saveTeamMember(newTeamMember);
    }

    /**
     * 소속팀 가입요청 거절 API
     * @param joinRequest
     */
    public void rejectJoinRequest(CmnMyTeamDTO joinRequest)
    {
        TeamJoinRequest rejectionInfo = TeamJoinRequest.rejectJoinRequest(joinRequest);

        boolean isRejectionSuccess = teamJoinRequestRepository.updateJoinRequestState(rejectionInfo) == 1;
        if (!isRejectionSuccess) {
            throw new CustomException(DomainErrorType.JOIN_REQUEST_NOT_FOUND);
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
                                player.positionCodeName(PositionCode.nameOf(player.getPositionCode()))
                                      .joinRequestStateCodeName(JoinRequestStateCode.nameOf(player.getJoinRequestStateCode()));
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
                                                .positionCodeName(PositionCode.nameOf(player.getPositionCode()))
                                                .joinRequestStateCodeName(JoinRequestStateCode.nameOf(player.getJoinRequestStateCode()));
        });

        return players;
    }

    /**
     * 소속팀 회원 강퇴시키기
     */
    public void dismissTeamMember( TeamAuthCommand command ) {
        TeamMember teamMember = teamMemberRepository.findByTeamMemberSeq( command.getTeamMemberSeq() );
        if ( !teamMember.isJoinTeam( command.getTeamSeq() ) ) {
            throw new CustomException( DomainErrorType.NO_JOIN_TEAM_MEMBER );
        }
        teamMemberRepository.updateWithdrawalState( teamMember.toWithdrawal() );
    }

    /**
     * 소속팀 관리자 임명하기
     */
    public void appointManager( TeamAuthCommand command ) {
        TeamMember teamMember       = teamMemberRepository.findByTeamMemberSeq( command.getTeamMemberSeq() );
        if ( !teamMember.isJoinTeam( command.getTeamSeq() ) ) {
            throw new CustomException( DomainErrorType.NO_JOIN_TEAM_MEMBER );
        }
        if ( !teamMember.checkTeamMemberAuth() ) {
            throw new CustomException( DomainErrorType.INVALID_STATE_FOR_MANAGER_AUTH );
        }
        teamMemberRepository.updateTeamAuth( teamMember.toManager() );
    }

    /**
     * 소속팀 관리자 해임하기
     */
    public void dismissManager( TeamAuthCommand command ) {
        TeamMember manager  = teamMemberRepository.findByTeamMemberSeq( command.getTeamMemberSeq() );
        if ( !manager.isJoinTeam( command.getTeamSeq() ) ) {
            throw new CustomException( DomainErrorType.NO_JOIN_TEAM_MEMBER );
        }
        if ( !manager.checkManagerAuth() ) {
            throw new CustomException( DomainErrorType.INVALID_STATE_FOR_TEAM_MEMBER_AUTH );
        }
        teamMemberRepository.updateTeamAuth( manager.toMember() );
    }
}
