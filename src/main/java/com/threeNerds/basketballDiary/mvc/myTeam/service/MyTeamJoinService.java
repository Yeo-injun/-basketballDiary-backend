package com.threeNerds.basketballDiary.mvc.myTeam.service;

import com.threeNerds.basketballDiary.constant.code.type.JoinRequestStateCode;
import com.threeNerds.basketballDiary.constant.code.type.JoinRequestTypeCode;
import com.threeNerds.basketballDiary.constant.code.type.PositionCode;
import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.error.DomainErrorType;
import com.threeNerds.basketballDiary.mvc.myTeam.domain.TeamJoinRequest;
import com.threeNerds.basketballDiary.mvc.myTeam.domain.TeamMember;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.CmnMyTeamDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.InvitationDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.repository.InvitationRepository;
import com.threeNerds.basketballDiary.mvc.myTeam.repository.TeamJoinRequestRepository;
import com.threeNerds.basketballDiary.mvc.myTeam.repository.TeamMemberRepository;
import com.threeNerds.basketballDiary.mvc.myTeam.service.dto.InvitationCommand;
import com.threeNerds.basketballDiary.mvc.myTeam.service.dto.InvitationQuery;
import com.threeNerds.basketballDiary.mvc.team.dto.PlayerDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.threeNerds.basketballDiary.exception.error.DomainErrorType.USER_NOT_FOUND;

/**
 * 팀 가입과 관련된 서비스
 * - 팀 가입은 2가지 방식으로 가능하다.
 *  1. 팀이 사용자를 초대하는 방법. ( Invitation )
 *  2. 사용자가 팀에게 가입을 요청하는 방법 ( JoinRequest )
 *
 * issue and history
 * <pre>
 * 2024.08.27 여인준 : 최초 생성 cf. TeamMemberManagerService에서 팀 가입 업무 분리
 * </pre>
 */

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MyTeamJoinService {

    private final TeamJoinRequestRepository teamJoinRequestRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final InvitationRepository invitationRepository;

    /**
     * 초대 요청 보내기
     */
    public void inviteUser( InvitationCommand command ) {
        TeamJoinRequest invitation = TeamJoinRequest.createInvitation( command.getTeamSeq(), command.getUserSeq() );

        /** 초대-가입요청 존재여부 확인 : 대기중인 가입요청 혹은 초대가 있을 경우 중복가입요청 방지 */
        int pendingCount = teamJoinRequestRepository.checkPendingJoinRequest( invitation );
        if ( pendingCount > 0 ) {
            throw new CustomException( DomainErrorType.ALREADY_EXIST_JOIN_REQUEST );
        }

        /** 팀원으로 존재하는지 확인 : 팀원으로 존재할 경우 예외를 던짐(409에러) */

        TeamMember teamMember = teamMemberRepository.findTeamMember( TeamMember.of( invitation ) );
        if ( null != teamMember ) {
            throw new CustomException(DomainErrorType.ALREADY_EXIST_TEAM_MEMBER);
        }

        /** 초대-가입요청이 없고, 팀원이 아닐 경우에만 INSERT */
        teamJoinRequestRepository.createJoinRequest( invitation );
    }

    /**
     * 초대한 사용자 목록 조회
     */
    public InvitationQuery.Result getInvitations( InvitationQuery query ) {
        TeamMember teamMember = teamMemberRepository.findTeamMemberByUserAndTeamSeq(
                TeamMember.builder()
                        .userSeq(   query.getUserSeq() )
                        .teamSeq(   query.getTeamSeq() )
                        .build()
        );
        if ( null == teamMember ) {
            throw new CustomException( DomainErrorType.NO_JOIN_TEAM_MEMBER );
        }
        InvitationDTO invitationParam = InvitationDTO.of( teamMember.getTeamSeq(), query.getJoinRequestState() );
        return query.buildResult( invitationRepository.findAllNotApproval( invitationParam ) );
    }
    
    /**
     * 소속팀 가입요청 승인 API
     * @param joinRequest
     */
    public void approveJoinRequest( CmnMyTeamDTO joinRequest ) {
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
    public void rejectJoinRequest(CmnMyTeamDTO joinRequest) {
        TeamJoinRequest rejectionInfo = TeamJoinRequest.rejectJoinRequest(joinRequest);

        boolean isRejectionSuccess = teamJoinRequestRepository.updateJoinRequestState(rejectionInfo) == 1;
        if (!isRejectionSuccess) {
            throw new CustomException(DomainErrorType.JOIN_REQUEST_NOT_FOUND);
        }
    }



    /**
     * 소속팀에 가입요청한 선수목록 조회 API
     * @param playerSearchCond
     * @return List<PlayerDTO>
     */
    public List<PlayerDTO> getJoinRequest( CmnMyTeamDTO playerSearchCond ) {
        playerSearchCond.joinRequestTypeCode(JoinRequestTypeCode.JOIN_REQUEST.getCode());
        List<PlayerDTO> players = new ArrayList<>(); // TODO 구현 예정

        players.stream().forEach(player -> { player
                                                .positionCodeName(PositionCode.nameOf(player.getPositionCode()))
                                                .joinRequestStateCodeName(JoinRequestStateCode.nameOf(player.getJoinRequestStateCode()));
        });

        return players;
    }
}
