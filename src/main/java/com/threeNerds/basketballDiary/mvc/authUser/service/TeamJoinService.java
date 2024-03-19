package com.threeNerds.basketballDiary.mvc.authUser.service;

import com.threeNerds.basketballDiary.constant.code.type.JoinRequestTypeCode;
import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.mvc.authUser.dto.CmnLoginUserDTO;
import com.threeNerds.basketballDiary.mvc.authUser.dto.JoinRequestDTO;
import com.threeNerds.basketballDiary.mvc.authUser.repository.dto.UserTeamManagerRepository;
import com.threeNerds.basketballDiary.mvc.authUser.service.dto.JoinRequestCommandDTO;
import com.threeNerds.basketballDiary.mvc.authUser.service.dto.JoinRequestQueryDTO;
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

    // 농구팀에 가입요청 보내기 ( 사용자 -> 농구팀 )
    public void sendRequest( JoinRequestCommandDTO command ) {
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

    // 사용자가 가입요청한 건을 취소상태로 바꾸는 API (데이터를 삭제하지는 않음)
    public void cancelRequest( JoinRequestCommandDTO command ) {
        TeamJoinRequest joinRequestCancel = TeamJoinRequest.cancelJoinRequest( command );

        boolean isCancelSuccess = teamJoinRequestRepository.updateJoinRequestState(joinRequestCancel) == 1;
        if ( !isCancelSuccess ) {
            throw new CustomException(JOIN_REQUEST_NOT_FOUND);
        }
    }

    // 사용자가 가입요청을 보낸 팀 목록을 조회
    public List<JoinRequestDTO> getJoinRequests( JoinRequestQueryDTO query ) {
        JoinRequestDTO joinRequestDTO = new JoinRequestDTO()
                                            .userSeq(               query.getUserSeq() )
                                            .joinRequestTypeCode(   JoinRequestTypeCode.JOIN_REQUEST.getCode() );

        return userTeamManagerRepository.findJoinRequestsByType( joinRequestDTO )
                                        .stream()
                                        .map( JoinRequestDTO::setCodeNameByInstanceCodeValue )
                                        .collect( Collectors.toList() );
    }

    // 사용자가 초대 받은 팀 목록을 조회
    public List<JoinRequestDTO> getJoinRequestsFrom(CmnLoginUserDTO loginUserDTO) {
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


    // 사용자가 팀의 초대를 승인하는 API
    public void approveInvitation(CmnLoginUserDTO loginUserDTO) {
        /** 초대요청 상태 업데이트 하기 */
        boolean isSuccess = teamJoinRequestRepository.updateJoinRequestState(TeamJoinRequest.approveInvitation(loginUserDTO)) == 1;
        if (!isSuccess) {
            throw new CustomException(JOIN_REQUEST_NOT_FOUND);
        }

        /** 팀원 추가 */
        TeamJoinRequest joinInfo = teamJoinRequestRepository.findUserByTeamJoinRequestSeq(loginUserDTO.getTeamJoinRequestSeq());
        TeamMember newTeamMember = TeamMember.create(joinInfo);
        teamMemberRepository.saveTeamMember(newTeamMember);
    }

    // 팀 초대 거절 API
    public void rejectInvitation(CmnLoginUserDTO loginUserDTO) {
        TeamJoinRequest rejectInvitation = TeamJoinRequest.rejectInvitation(loginUserDTO);

        boolean isSussess = teamJoinRequestRepository.updateJoinRequestState(rejectInvitation) == 1 ? true : false;
        if (!isSussess)
        {
            throw new CustomException(INVITATION_NOT_FOUND);
        }

    }
}
