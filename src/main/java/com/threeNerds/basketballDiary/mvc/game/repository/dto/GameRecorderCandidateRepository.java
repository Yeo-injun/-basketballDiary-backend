package com.threeNerds.basketballDiary.mvc.game.repository.dto;

import com.threeNerds.basketballDiary.mvc.game.dto.GameRecorderCandidateDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface GameRecorderCandidateRepository {

    List<GameRecorderCandidateDTO> findAllCandidates( GameRecorderCandidateDTO query );
}

