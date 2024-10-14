package com.threeNerds.basketballDiary.mvc.team.mapper;

import com.threeNerds.basketballDiary.mvc.team.mapper.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeamMemberMapper {

    // 운영진 목록 리스트 조회
    // 팀원 목록 페이징 조회
    /** 페이징된 소속팀 목록 조회 */
    List<MyTeamDTO> findPagingMyTeams(SearchMyTeamDTO cond );

    /**
     * 리팩토링 완료 메소드
     */
    /**----------------------
     * 소속팀 프로필 조회
     *----------------------*/
    ProfileDTO findProfileByUserSeqAndTeamSeq( ProfileDTO profileDTO );

    /**----------------------
     * 소속팀 운영진 목록 ( 팀장 및 관리자 )
     *----------------------*/
    List<MemberDTO> findAllManagersByTeamSeq( Long teamSeq );

    /**----------------------
     * 소속팀 팀원 목록 ( 운영진 제외 )
     * - 페이징 반영
     *----------------------*/
    List<MemberDTO> findPaginationTeamMembers( SearchMemberDTO cond );
    int findTotalCountTeamMembers( SearchMemberDTO cond );

    /**----------------------
     * 소속팀 전체 팀원 목록 ( 운영진 포함 )
     * - 페이징 반영
     *----------------------*/
    List<MemberDTO> findPaginationAllTeamMembers( SearchMemberDTO cond );
    int findTotalCountAllTeamMembers( SearchMemberDTO cond );


}
