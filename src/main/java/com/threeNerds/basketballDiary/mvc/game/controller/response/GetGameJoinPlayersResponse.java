package com.threeNerds.basketballDiary.mvc.game.controller.response;

import com.threeNerds.basketballDiary.constant.code.type.HomeAwayCode;
import com.threeNerds.basketballDiary.mvc.game.dto.GameJoinTeamDTO;
import com.threeNerds.basketballDiary.mvc.game.dto.GameJoinTeamInfoDTO;
import com.threeNerds.basketballDiary.mvc.game.dto.PlayerInfoDTO;
import com.threeNerds.basketballDiary.pagination.Pagination;
import lombok.Getter;

import java.util.List;


@Getter
public class GetGameJoinPlayersResponse {
    private Long gameSeq;
    private String homeAwayCode;
    private String homeAwayCodeName;
    private Long teamSeq;
    private Pagination pagination;
    private List<PlayerInfoDTO> players;

    public GetGameJoinPlayersResponse (Long gameSeq, String homeAwayCode, Long teamSeq, Pagination pagination, List<PlayerInfoDTO> players ) {
        this.gameSeq            = gameSeq;
        this.teamSeq            = teamSeq;
        this.homeAwayCode       = homeAwayCode;
        this.homeAwayCodeName   = HomeAwayCode.nameOf( homeAwayCode );
        this.pagination         = pagination;
        this.players            = players;
    }
}
