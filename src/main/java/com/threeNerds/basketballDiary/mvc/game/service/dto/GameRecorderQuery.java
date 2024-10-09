package com.threeNerds.basketballDiary.mvc.game.service.dto;

import com.threeNerds.basketballDiary.mvc.game.mapper.dto.GameRecorderDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameRecorderQuery {
    private Long gameSeq;

    @Getter
    public class Result {

        List<GameRecorderDTO> recorders;

        Result( List<GameRecorderDTO> recorders ) {
            this.recorders = recorders;
        }
    }

    public GameRecorderQuery.Result buildResult( List<GameRecorderDTO> recorders ) {
        return new Result( recorders );
    }
}
