package com.threeNerds.basketballDiary.mvc.game.service.dto;


import com.threeNerds.basketballDiary.mvc.game.dto.PlayerInfoDTO;
import com.threeNerds.basketballDiary.pagination.Pagination;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameJoinPlayerQuery {

    private Long gameSeq;
    private String homeAwayCode;
    private Integer pageNo;

    @Getter
    public class Result {
        private Long gameSeq;
        private Long teamSeq;
        private Pagination pagination;
        private List<PlayerInfoDTO> players;

        Result( GameJoinPlayerQuery query, Long teamSeq, List<PlayerInfoDTO> players, Pagination pagination ) {
            this.gameSeq = query.getGameSeq();
            this.teamSeq = teamSeq;
            this.players = players;
            this.pagination = pagination;
        }
    }

    public Result buildResult ( Long teamSeq, List<PlayerInfoDTO> players, Pagination pagination ) {
        return new Result( this, teamSeq, players, pagination );
    }
}
