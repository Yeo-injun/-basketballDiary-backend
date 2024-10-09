package com.threeNerds.basketballDiary.mvc.game.controller.response;

import com.threeNerds.basketballDiary.mvc.game.mapper.dto.GameRecorderCandidateDTO;
import com.threeNerds.basketballDiary.mvc.game.service.dto.GameRecorderCandidatesQuery;
import lombok.Getter;

import java.util.List;

@Getter
public class GetGameRecorderCandidatesResponse {

    private final List<GameRecorderCandidateDTO> candidates;

    public GetGameRecorderCandidatesResponse( GameRecorderCandidatesQuery.Result result ) {
        this.candidates = result.getCandidates();
    }
}
