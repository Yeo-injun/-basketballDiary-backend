package com.threeNerds.basketballDiary.mvc.game.controller.response;

import com.threeNerds.basketballDiary.mvc.game.mapper.dto.GameOpponentDTO;
import com.threeNerds.basketballDiary.mvc.game.service.dto.OppenentTeamQuery;
import com.threeNerds.basketballDiary.pagination.Pagination;
import lombok.Getter;

import java.util.List;

@Getter
public class SearchOpponentsResponse {

    private Pagination pagination;
    private List<GameOpponentDTO> opponents;

    public SearchOpponentsResponse( OppenentTeamQuery.Result queryResult ) {
        this.pagination = queryResult.getPagination();
        this.opponents  = queryResult.getOpponents();
    }
}
