package com.threeNerds.basketballDiary.mvc.game.repository.dto;

import com.threeNerds.basketballDiary.mvc.game.dto.GameRecorderCandidateDTO;
import com.threeNerds.basketballDiary.mvc.game.dto.getGameRecorders.response.GameRecorderDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface GameRecorderRepository {

    List<GameRecorderCandidateDTO> findAllCandidates( GameRecorderCandidateDTO query );

    List<GameRecorderDTO> findAllRecorders( Long gameSeq );
}

