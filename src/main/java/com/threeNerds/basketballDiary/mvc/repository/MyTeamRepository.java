package com.threeNerds.basketballDiary.mvc.repository;

import com.threeNerds.basketballDiary.mvc.dto.myTeam.myTeam.MemberDTO;
import com.threeNerds.basketballDiary.mvc.dto.myTeam.myTeam.MyTeamInfoDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MyTeamRepository {

    List<MyTeamInfoDTO> findAllByUserSeq(Long userSeq);
    MyTeamInfoDTO findByUserSeqAndTeamSeq(Long userSeq, Long teamSeq);
    List<MemberDTO> findAllManagerByTeamSeq(Long teamSeq);
    List<MemberDTO> findAllMemberByTeamSeq(Long teamSeq);
    List<MemberDTO> findPagingMemberByTeamSeq(MemberDTO memberDTO);
}
