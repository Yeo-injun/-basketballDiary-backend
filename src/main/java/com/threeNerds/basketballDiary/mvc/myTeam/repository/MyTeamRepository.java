package com.threeNerds.basketballDiary.mvc.myTeam.repository;

import com.threeNerds.basketballDiary.mvc.myTeam.dto.FindMyTeamProfileDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.MemberDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.MyTeamDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.SearchMyTeamDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MyTeamRepository {


    // 소속팀 단건 조회
    MyTeamDTO findByUserSeqAndTeamSeq(FindMyTeamProfileDTO paramDTO);
    // 프로필 조회
    MemberDTO findProfileByUserSeqAndTeamSeq(FindMyTeamProfileDTO userDto);
    // 운영진 목록 리스트 조회
    List<MemberDTO> findAllManagerByTeamSeq(Long teamSeq);
    // 팀원 목록 리스트 조회
    List<MemberDTO> findAllMemberByTeamSeq(Long teamSeq);
    // 팀원 목록 페이징 조회
    List<MemberDTO> findPagingMemberByTeamSeq(MemberDTO memberDTO);

    /**
     * 페이징된 소속팀 목록 조회
     * @param searchMyTeamDTO
     * @return
     */
    List<MyTeamDTO> findPagingMyTeams(SearchMyTeamDTO searchMyTeamDTO);

}
