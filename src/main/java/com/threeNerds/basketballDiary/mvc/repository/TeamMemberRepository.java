package com.threeNerds.basketballDiary.mvc.repository;

import com.threeNerds.basketballDiary.mvc.controller.MyTeamController;
import com.threeNerds.basketballDiary.mvc.domain.TeamMember;
import com.threeNerds.basketballDiary.mvc.dto.loginUser.userTeamManager.JoinRequestDTO;
import com.threeNerds.basketballDiary.mvc.dto.myTeam.FindMyTeamProfileDTO;
import com.threeNerds.basketballDiary.mvc.dto.myTeam.ModifyMyTeamProfileDTO;
import com.threeNerds.basketballDiary.mvc.dto.myTeam.ResponseMyTeamProfileDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TeamMemberRepository {
    /**
     * 팀원 등록
     * @param teamMember
     * @return
     */
    int saveTeamMemeber(TeamMember teamMember);

    /**
     * 팀원 중복여부 확인
     * @param joinRequest
     * @return JoinRequestDTO
     */
    JoinRequestDTO checkTeamMember(JoinRequestDTO joinRequest);

    /**
     * 팀원 단건조회(팀원SEQ로)
     * @param teamMemberSeq
     * @return
     */
    TeamMember findByTeamMemberSeq(Long teamMemberSeq);

    /**
     * 팀원 강퇴상태로 수정
     * @param teamMember
     * @return
     */
    int updateWithdrawalState(TeamMember teamMember);

    /**
     * 팀원 권한정보 수정
     * @param teamMember
     * @return int
     */
    int updateTeamAuth(TeamMember teamMember);

    /**
     * 소속팀 프로필 조회
     * @param userDto
     * @return
     */
    ResponseMyTeamProfileDTO findMyTeamProfile(FindMyTeamProfileDTO userDto);

    int updateMyTeamProfile(ModifyMyTeamProfileDTO userDto);

    void deleteMyTeamProfile(FindMyTeamProfileDTO userDto);

    Long findMyTeamCount(Long userSeq);
}
