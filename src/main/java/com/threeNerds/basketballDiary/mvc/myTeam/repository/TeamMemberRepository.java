package com.threeNerds.basketballDiary.mvc.myTeam.repository;

import com.threeNerds.basketballDiary.mvc.myTeam.domain.TeamJoinRequest;
import com.threeNerds.basketballDiary.mvc.myTeam.domain.TeamMember;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeamMemberRepository {
    /**********
     * SELECT
     **********/
    /** 팀원 조회 */
    TeamMember findTeamMember( TeamJoinRequest joinRequest );
    TeamMember findTeamMember( TeamMember teamMember );

    /** 팀원 단건조회(팀원SEQ로) */
    TeamMember findByTeamMemberSeq(Long teamMemberSeq);

    /** 사용자 및 팀Seq로 팀원정보 조회 */
    TeamMember findTeamMemberByUserAndTeamSeq(TeamMember tmParam);

    /** 사용자가 소속된 모든 팀의 팀원정보 조회 */
    List<TeamMember> findAllJoinTeamsByUserSeq( TeamMember teamMember );


    /**********
     * INSERT
     **********/
    /** 팀원 등록  */
    int saveTeamMember(TeamMember teamMember);

    /**********
     * UPDATE
     **********/
    /** 팀원 강퇴상태로 수정 */
    int updateWithdrawalState( TeamMember teamMember );

    /** 팀원의 소속팀 프로필 수정 */
    int updateMyTeamProfile( TeamMember teamMember );

    /** 팀원 권한정보 수정 */
    int updateTeamAuth( TeamMember teamMember );

    /**********
     * DELETE
     **********/
    int deleteTeamMemberByUserSeqAndTeamSeq( TeamMember teamMember );
    int deleteAllByTeamSeq( Long teamSeq );
}
