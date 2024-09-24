package com.threeNerds.basketballDiary.mvc.myTeam.repository;

import com.threeNerds.basketballDiary.mvc.myTeam.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MyTeamRepository {

    // 소속팀 단건 조회
    TeamInfoDTO findByUserSeqAndTeamSeq( FindTeamInfoDTO cond );
    // 운영진 목록 리스트 조회
    List<MemberDTO> findAllManagerByTeamSeq( Long teamSeq );
    // 팀원 목록 페이징 조회
    /** 페이징된 소속팀 목록 조회 */
    List<MyTeamDTO> findPagingMyTeams( SearchMyTeamDTO cond );

    List<MemberDTO> findPagingMemberByTeamSeq( MemberDTO cond );

    /** 소속팀원 전체 조회 - 페이징 처리 */
    List<MemberDTO> findAllTeamMemberPaging( MemberDTO cond );

    /**
     * 리팩토링 완료 메소드
     */
    /**----------------------
     * 소속팀 프로필 조회
     *----------------------*/
    ProfileDTO findProfileByUserSeqAndTeamSeq( ProfileDTO profileDTO );

}
