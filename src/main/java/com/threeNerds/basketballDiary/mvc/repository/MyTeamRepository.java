package com.threeNerds.basketballDiary.mvc.repository;

import com.threeNerds.basketballDiary.mvc.dto.MyTeamDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MyTeamRepository {

    List<MyTeamDTO> findAllByUserSeq(Long userSeq);
    MyTeamDTO findByUserSeqAndTeamSeq(Long userSeq, Long teamSeq);

}
