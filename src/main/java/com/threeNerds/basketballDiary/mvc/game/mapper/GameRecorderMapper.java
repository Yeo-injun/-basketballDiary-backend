package com.threeNerds.basketballDiary.mvc.game.mapper;

import com.threeNerds.basketballDiary.mvc.game.mapper.dto.GameRecorderCandidateDTO;
import com.threeNerds.basketballDiary.mvc.game.mapper.dto.GameRecorderDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface GameRecorderMapper {

    List<GameRecorderCandidateDTO> findAllCandidates( GameRecorderCandidateDTO query );

    List<GameRecorderDTO> findAllRecorders(Long gameSeq );
}

