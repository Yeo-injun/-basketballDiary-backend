package com.threeNerds.basketballDiary.mvc.team.service.dto;

import com.threeNerds.basketballDiary.mvc.team.dto.TeamDTO;
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
public class TeamQuery {

    private String teamName;
    private String sigungu;
    private String startDay;
    private String endDay;
    private String startTime;
    private String endTime;
    private Integer pageNo;

    @Getter
    public class Result {

        private Pagination pagination;
        private List<TeamDTO> teams;

        Result( Pagination pagination, List<TeamDTO> teams ) {
            this.pagination = pagination;
            this.teams      = teams;
        }
    }

    public Result buildResult( Pagination pagination, List<TeamDTO> teams ) {
        return new Result( pagination, teams );
    }
}
