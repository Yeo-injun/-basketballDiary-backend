package com.threeNerds.basketballDiary.mvc.myTeam.service;

import com.threeNerds.basketballDiary.constant.code.type.JoinRequestStateCode;
import com.threeNerds.basketballDiary.constant.code.type.JoinRequestTypeCode;
import com.threeNerds.basketballDiary.constant.code.type.PositionCode;
import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.error.DomainErrorType;
import com.threeNerds.basketballDiary.mvc.myTeam.domain.TeamJoinRequest;
import com.threeNerds.basketballDiary.mvc.myTeam.domain.TeamMember;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.InvitationDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.repository.InvitationRepository;
import com.threeNerds.basketballDiary.mvc.myTeam.service.dto.InvitationQuery;
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

@Deprecated
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class TeamMemberManagerService {

    private final TeamJoinRequestRepository teamJoinRequestRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final PlayerRepository playerRepository;


    /**
     * 소속팀 가입요청 거절 API
     * TODO MyTeamJoinService로 이전
     */
    @Deprecated
    public void rejectJoinRequest(CmnMyTeamDTO joinRequest)
    {
        TeamJoinRequest rejectionInfo = TeamJoinRequest.rejectJoinRequest(joinRequest);

        boolean isRejectionSuccess = teamJoinRequestRepository.updateJoinRequestState(rejectionInfo) == 1;
        if (!isRejectionSuccess) {
            throw new CustomException(DomainErrorType.JOIN_REQUEST_NOT_FOUND);
        }
    }



    /**
     * 소속팀에 가입요청한 선수목록 조회 API
     * TODO MyTeamJoinService로 이전
     * @return List<PlayerDTO>
     */
    @Deprecated
    public List<PlayerDTO> searchJoinRequestPlayer(CmnMyTeamDTO playerSearchCond) {
        playerSearchCond.joinRequestTypeCode(JoinRequestTypeCode.JOIN_REQUEST.getCode());
        List<PlayerDTO> players = playerRepository.findPlayers(playerSearchCond);

        players.stream().forEach(player -> { player
                                                .positionCodeName(PositionCode.nameOf(player.getPositionCode()))
                                                .joinRequestStateCodeName(JoinRequestStateCode.nameOf(player.getJoinRequestStateCode()));
        });

        return players;
    }

}
