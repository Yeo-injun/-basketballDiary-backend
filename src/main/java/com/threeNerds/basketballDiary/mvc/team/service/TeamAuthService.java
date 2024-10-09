package com.threeNerds.basketballDiary.mvc.team.service;

import com.threeNerds.basketballDiary.auth.constant.AuthLevel;
import com.threeNerds.basketballDiary.auth.constant.AuthType;
import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.error.DomainErrorType;
import com.threeNerds.basketballDiary.mvc.team.domain.TeamMember;
import com.threeNerds.basketballDiary.mvc.team.domain.repository.TeamMemberRepository;
import com.threeNerds.basketballDiary.mvc.team.service.dto.TeamAuthCommand;
import com.threeNerds.basketballDiary.mvc.team.service.dto.TeamAuthQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *  Service
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
public class TeamAuthService {

    /**--------------------------------------
     * Repository
     **--------------------------------------*/
    private final TeamMemberRepository teamMemberRepo;
    
    public TeamAuthQuery.Result getAllTeamAuthInfo( TeamAuthQuery query ) {

        Long userSeq = query.getUserSeq();
        TeamMember teamMemberParam = TeamMember.builder()
                                        .userSeq( userSeq )
                                        .build();
        // 소속된 팀 목록 조회
        List<TeamMember> joinTeamList = teamMemberRepo.findAllJoinTeamsByUserSeq( teamMemberParam );
        Map< Long, AuthLevel > teamAuthMap = joinTeamList.stream()
                                                    .collect( Collectors.toMap(
                                                                                TeamMember::getTeamSeq,
                                                                                item -> AuthLevel.of( AuthType.TEAM, Integer.parseInt( item.getTeamAuthCode() ) )
                                                                                )
                                                    );
        return query.buildResult( teamAuthMap );
    }

    /**
     * 소속팀 회원 강퇴시키기
     */
    public void dismissTeamMember( TeamAuthCommand command ) {
        TeamMember teamMember = teamMemberRepo.findByTeamMemberSeq( command.getTeamMemberSeq() );
        if ( !teamMember.isJoinTeam( command.getTeamSeq() ) ) {
            throw new CustomException( DomainErrorType.NO_JOIN_TEAM_MEMBER );
        }
        teamMemberRepo.updateWithdrawalState( teamMember.toWithdrawal() );
    }

    /**
     * 소속팀 관리자 임명하기
     */
    public void appointManager( TeamAuthCommand command ) {
        TeamMember teamMember       = teamMemberRepo.findByTeamMemberSeq( command.getTeamMemberSeq() );
        if ( !teamMember.isJoinTeam( command.getTeamSeq() ) ) {
            throw new CustomException( DomainErrorType.NO_JOIN_TEAM_MEMBER );
        }
        if ( !teamMember.checkTeamMemberAuth() ) {
            throw new CustomException( DomainErrorType.INVALID_STATE_FOR_MANAGER_AUTH );
        }
        teamMemberRepo.updateTeamAuth( teamMember.toManager() );
    }

    /**
     * 소속팀 관리자 해임하기
     */
    public void dismissManager( TeamAuthCommand command ) {
        TeamMember manager  = teamMemberRepo.findByTeamMemberSeq( command.getTeamMemberSeq() );
        if ( !manager.isJoinTeam( command.getTeamSeq() ) ) {
            throw new CustomException( DomainErrorType.NO_JOIN_TEAM_MEMBER );
        }
        if ( !manager.checkManagerAuth() ) {
            throw new CustomException( DomainErrorType.INVALID_STATE_FOR_TEAM_MEMBER_AUTH );
        }
        teamMemberRepo.updateTeamAuth( manager.toMember() );
    }
}
