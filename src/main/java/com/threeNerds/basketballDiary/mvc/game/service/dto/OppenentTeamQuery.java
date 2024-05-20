package com.threeNerds.basketballDiary.mvc.game.service.dto;

import com.threeNerds.basketballDiary.mvc.game.dto.GameOpponentDTO;
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
public class OppenentTeamQuery {

    String sidoCode;    /*시도코드*/
    String teamName;    /*팀이름*/
    String leaderName;  /*리더이름*/
    Integer pageNo;     /* 페이징 : 현재 페이지 */

    /**
     * 결과값
     */
    @Getter
    public class Result {
        Pagination pagination;
        List<GameOpponentDTO> opponents;

        Result( Pagination pagination, List<GameOpponentDTO> opponents ) {
            this.pagination = pagination;
            this.opponents  = opponents;
        }
    }

    public OppenentTeamQuery.Result setResult( Pagination pagination, List<GameOpponentDTO> opponents ) {
        return new Result( pagination, opponents );
    }

}
