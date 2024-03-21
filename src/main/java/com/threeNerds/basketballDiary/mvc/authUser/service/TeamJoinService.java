package com.threeNerds.basketballDiary.mvc.authUser.service;

import com.threeNerds.basketballDiary.constant.code.type.JoinRequestTypeCode;
import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.mvc.authUser.dto.JoinRequestDTO;
import com.threeNerds.basketballDiary.mvc.authUser.repository.dto.UserTeamManagerRepository;
import com.threeNerds.basketballDiary.mvc.authUser.service.dto.TeamInvitationCommand;
import com.threeNerds.basketballDiary.mvc.authUser.service.dto.JoinRequestCommand;
import com.threeNerds.basketballDiary.mvc.authUser.service.dto.JoinRequestQuery;
import com.threeNerds.basketballDiary.mvc.authUser.service.dto.TeamInvitationQuery;
import com.threeNerds.basketballDiary.mvc.myTeam.domain.TeamJoinRequest;
import com.threeNerds.basketballDiary.mvc.myTeam.domain.TeamMember;
import com.threeNerds.basketballDiary.mvc.myTeam.repository.TeamJoinRequestRepository;
import com.threeNerds.basketballDiary.mvc.myTeam.repository.TeamMemberRepository;
import com.threeNerds.basketballDiary.mvc.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.threeNerds.basketballDiary.exception.error.DomainErrorType.*;

/**
 * 사용자가 팀에 가입하기 위한 service
 * @author 여인준
 *
 * issue and history
 * <pre>
 * 2024.03.19 여인준 : 소스코드 이전 ( UserTeamManagerService의 역할을 명확히 하기 위함 )
 * </pre>
 */

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class TeamJoinService {

    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final TeamJoinRequestRepository teamJoinRequestRepository;
    private final UserTeamManagerRepository userTeamManagerRepository;

    /**-------------------------------
     * 사용자가 팀에 가입요청 보내기
     **-------------------------------*/
    public void sendRequest( JoinRequestCommand command ) {
        TeamJoinRequest joinRequestInfo = TeamJoinRequest.createJoinRequest( command );
        /** 초대-가입요청 존재여부 확인 : 대기중인 가입요청 혹은 초대가 있을 경우 중복가입요청 방지 */
        boolean hasPendingJoinRequest = teamJoinRequestRepository.checkPendingJoinRequest( joinRequestInfo ) > 0;
        if ( hasPendingJoinRequest ) {
            throw new CustomException( ALREADY_EXIST_JOIN_REQUEST );
        }

        /** 팀 존재여부 확인 */
        if ( null == teamRepository.findByTeamSeq( joinRequestInfo.getTeamSeq() ) ) {
            throw new CustomException( TEAM_NOT_FOUND );
        }

        /** 이미 팀원으로 존재하는지 확인 */
        TeamMember teamMember = teamMemberRepository.findTeamMember( joinRequestInfo );
        if ( teamMember != null ) {
            throw new CustomException(ALREADY_EXIST_TEAM_MEMBER);
        }

        /** 팀이 존재하고, 초대-가입요청이 없을경우에만 INSERT */
        teamJoinRequestRepository.createJoinRequest( joinRequestInfo );
    }

    /**-------------------------------
     * 사용자가 보낸 팀 가입요청 취소
     **-------------------------------*/
    public void cancelRequest( JoinRequestCommand command ) {
        TeamJoinRequest joinRequestCancel = TeamJoinRequest.cancelJoinRequest( command );

        boolean isCancelSuccess = teamJoinRequestRepository.updateJoinRequestState(joinRequestCancel) > 0;
        if ( !isCancelSuccess ) {
            throw new CustomException(JOIN_REQUEST_NOT_FOUND);
        }
    }

    /**-------------------------------
     * 사용자가 보낸 팀 가입요청 목록 조회
     **-------------------------------*/
    public List<JoinRequestDTO> getJoinRequests( JoinRequestQuery query ) {
        JoinRequestDTO joinRequestDTO = new JoinRequestDTO()
                                            .userSeq(               query.getUserSeq() )
                                            .joinRequestTypeCode(   JoinRequestTypeCode.JOIN_REQUEST.getCode() );

        return userTeamManagerRepository.findJoinRequestsByType( joinRequestDTO )
                                        .stream()
                                        .map( JoinRequestDTO::setCodeNameByInstanceCodeValue )
                                        .collect( Collectors.toList() );
    }


    /**-------------------------------
     * 사용자가 팀가입초대 승인
     **-------------------------------*/
    public void approveInvitation( TeamInvitationCommand command ) {
        /** 초대요청 상태 업데이트 하기 */
        boolean isSuccessApprove = teamJoinRequestRepository.updateJoinRequestState( TeamJoinRequest.approveInvitation( command ) ) > 0;
        if ( !isSuccessApprove ) {
            throw new CustomException( JOIN_REQUEST_NOT_FOUND );
        }

        /** 팀원 추가 */
        TeamJoinRequest joinInfo = teamJoinRequestRepository.findBySeq( command.getTeamJoinRequestSeq() );
        teamMemberRepository.saveTeamMember( TeamMember.of( joinInfo ) );
    }


    /**-------------------------------
     * 사용자가 팀가입초대 거절
     **-------------------------------*/
    public void rejectInvitation( TeamInvitationCommand command ) {
        TeamJoinRequest rejectInvitation = TeamJoinRequest.rejectInvitation( command );
        boolean isSussessRejection = teamJoinRequestRepository.updateJoinRequestState(rejectInvitation) > 0;
        if ( !isSussessRejection ) {
            throw new CustomException( NOT_FOUND_REJECT_INVITATION );
        }
    }


    /**-------------------------------
     * 사용자가 받은 팀가입초대 목록 조회
     **-------------------------------*/
    public List<JoinRequestDTO> getTeamInvitations( TeamInvitationQuery query ) {
        JoinRequestDTO joinRequestDTO = new JoinRequestDTO()
                .userSeq(               query.getUserSeq() )
                .joinRequestTypeCode(   JoinRequestTypeCode.INVITATION.getCode() );

        return userTeamManagerRepository.findJoinRequestsByType( joinRequestDTO )
                                                    .stream()
                                                    .map( JoinRequestDTO::setCodeNameByInstanceCodeValue )
                                                    .collect( Collectors.toList() );
    }

}
