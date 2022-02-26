package com.threeNerds.basketballDiary.mvc.repository;

import com.threeNerds.basketballDiary.mvc.domain.TeamMember;
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
     * @param teamMemberInfo
     * @return
     */
    int checkTeamMember(TeamMember teamMemberInfo);

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
}
