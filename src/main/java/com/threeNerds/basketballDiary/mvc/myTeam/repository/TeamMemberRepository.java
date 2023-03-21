package com.threeNerds.basketballDiary.mvc.myTeam.repository;

import com.threeNerds.basketballDiary.mvc.myTeam.domain.TeamJoinRequest;
import com.threeNerds.basketballDiary.mvc.myTeam.domain.TeamMember;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.CmnMyTeamDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.FindMyTeamProfileDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.ModifyMyTeamProfileDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TeamMemberRepository {
    /**********
     * SELECT
     **********/
    /** 팀원 중복여부 확인 TODO 삭제검토  */
    int checkDuplicatedTeamMember(CmnMyTeamDTO joinRequest);

    /** 팀원 조회 */
    TeamMember findTeamMember(TeamJoinRequest joinRequest);

    /** 팀원 단건조회(팀원SEQ로) */
    TeamMember findByTeamMemberSeq(Long teamMemberSeq);

    Long findMyTeamCount(Long userSeq);

    /** 사용자 및 팀Seq로 팀원정보 조회 */
    TeamMember findTeamMemberByUserAndTeamSeq(TeamMember tmParam);

    /**********
     * INSERT
     **********/
    /** 팀원 등록  */
    int saveTeamMemeber(TeamMember teamMember);

    /**********
     * UPDATE
     **********/
    /** 팀원 강퇴상태로 수정 */
    int updateWithdrawalState(TeamMember teamMember);

    int updateMyTeamProfile(ModifyMyTeamProfileDTO userDto);

    /** 팀원 권한정보 수정 */
    int updateTeamAuth(TeamMember teamMember);

    /**********
     * DELETE
     **********/
    int deleteMyTeamProfile(FindMyTeamProfileDTO userDto);

}
