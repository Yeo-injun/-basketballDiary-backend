package com.threeNerds.basketballDiary.mvc.team.dto;

import com.threeNerds.basketballDiary.pagination.Pagination;
import lombok.Getter;

@Getter
public class SearchTeamDTO {

    private String teamName;
    private String sigungu;
    private String startDay;
    private String endDay;
    private String startTime;
    private String endTime;
    private Pagination pagination;

    public SearchTeamDTO(
        String teamName,    String sigungu,
        String startDay,    String endDay,
        String startTime,   String endTime,
        Pagination pagination
    ) {
        this.teamName    = teamName;
        this.sigungu     = sigungu;
        this.startDay    = startDay;
        this.endDay      = endDay;
        this.startTime   = startTime;
        this.endTime     = endTime;
        this.pagination  = pagination;
    }
}
