package com.threeNerds.basketballDiary.mvc.repository;

import com.threeNerds.basketballDiary.mvc.dto.myTeam.myTeam.MemberDTO;
import com.threeNerds.basketballDiary.mvc.dto.myTeam.myTeam.MyTeamDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MyTeamRepository {

    // 소속팀 목록 리스트 조회
    List<MyTeamDTO> findAllByUserSeq(Long userSeq);
    // 소속팀 단건 조회
    MyTeamDTO findByUserSeqAndTeamSeq(Long userSeq, Long teamSeq);
    // 프로필 조회
    MemberDTO findProfileByUserSeqAndTeamSeq(Long userSeq, Long teamSeq);
    // 운영진 목록 리스트 조회
    List<MemberDTO> findAllManagerByTeamSeq(Long teamSeq);
    // 팀원 목록 리스트 조회
    List<MemberDTO> findAllMemberByTeamSeq(Long teamSeq);
    // 팀원 목록 페이징 조회
    List<MemberDTO> findPagingMemberByTeamSeq(MemberDTO memberDTO);

}
