package com.threeNerds.basketballDiary.mvc.repository;

import com.threeNerds.basketballDiary.mvc.dto.MyTeamDTO;
import com.threeNerds.basketballDiary.mvc.dto.MyTeamInfoDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MyTeamRepository {

    List<MyTeamInfoDTO> findAllByUserSeq(Long userSeq);
    MyTeamInfoDTO findByUserSeqAndTeamSeq(Long userSeq, Long teamSeq);

}
