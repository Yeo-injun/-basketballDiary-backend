package com.threeNerds.basketballDiary.mvc.game.service.dto;

import com.threeNerds.basketballDiary.mvc.game.mapper.dto.GameRecorderCandidateDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class GameRecorderCandidatesQuery {
    private Long gameSeq;
    private String homeAwayCode;

    @Getter
    public class Result {
        private List<GameRecorderCandidateDTO> candidates;

        Result( List<GameRecorderCandidateDTO> candidates ) {
            this.candidates = candidates;
        }
    }

    public Result buildResult( List<GameRecorderCandidateDTO> candidates ) {
        return new Result( candidates );
    }
}
