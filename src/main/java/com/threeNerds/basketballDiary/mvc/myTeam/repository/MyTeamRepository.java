package com.threeNerds.basketballDiary.mvc.myTeam.repository;

import com.threeNerds.basketballDiary.mvc.myTeam.dto.*;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.getMyTeamProfile.response.MyTeamProfileDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MyTeamRepository {

    // 소속팀 단건 조회
    TeamInfoDTO findByUserSeqAndTeamSeq( FindTeamInfoDTO params );
    // 운영진 목록 리스트 조회
    List<MemberDTO> findAllManagerByTeamSeq(Long teamSeq);
    // 팀원 목록 리스트 조회 TODO 삭제 검토
    List<MemberDTO> findAllMemberByTeamSeq(Long teamSeq);
    // 팀원 목록 페이징 조회
    /** 페이징된 소속팀 목록 조회 */
    List<MyTeamDTO> findPagingMyTeams(SearchMyTeamDTO searchMyTeamDTO);

    List<MemberDTO> findPagingMemberByTeamSeq(MemberDTO memberDTO);

    /** 소속팀원 전체 조회 - 페이징 처리 */
    List<MemberDTO> findAllTeamMemberPaging(MemberDTO searchMemebrCond);

    /**
     * 리팩토링 완료 메소드
     */
    /** 소속팀 프로필 조회 */
    MyTeamProfileDTO findProfileByUserSeqAndTeamSeq( MyTeamProfileDTO dto );

}
