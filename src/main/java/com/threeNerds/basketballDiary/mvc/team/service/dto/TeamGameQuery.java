package com.threeNerds.basketballDiary.mvc.team.service.dto;

import com.threeNerds.basketballDiary.mvc.team.dto.TeamGameDTO;
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
public class TeamGameQuery {

    /* 사용자Seq */
    private Long userSeq;
    /* 팀Seq */
    private Long teamSeq;
    /* 페이지 번호 */
    private Integer pageNo;

    private String gameBgngYmd;
    private String gameEndYmd;
    private String sidoCode;
    private String gamePlaceName;
    private String gameTypeCode;
    private String homeAwayCode;


    @Getter
    public class Result {

        private List<TeamGameDTO> games;
        private Pagination pagination;

        Result( List<TeamGameDTO> games, Pagination pagination ) {
            this.games      = games;
            this.pagination = pagination;
        }
    }

    public Result buildResult( List<TeamGameDTO> games, Pagination pagination ) {
        return new Result( games, pagination );
    }
}
