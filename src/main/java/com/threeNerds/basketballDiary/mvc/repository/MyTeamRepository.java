package com.threeNerds.basketballDiary.mvc.repository;

import com.threeNerds.basketballDiary.mvc.dto.MemberDTO;
import com.threeNerds.basketballDiary.mvc.dto.MyTeamInfoDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MyTeamRepository {

    List<MyTeamInfoDTO> findAllByUserSeq(Long userSeq);
    MyTeamInfoDTO findByUserSeqAndTeamSeq(Long userSeq, Long teamSeq);
    List<MemberDTO> findAllManagerByTeamSeq(Long teamSeq);
    List<MemberDTO> findAllMemberByTeamSeq(Long teamSeq);
}
