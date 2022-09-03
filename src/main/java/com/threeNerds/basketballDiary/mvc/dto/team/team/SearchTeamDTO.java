package com.threeNerds.basketballDiary.mvc.dto.team.team;

import com.threeNerds.basketballDiary.mvc.dto.pagination.PagerDTO;
import lombok.Getter;

@Getter
public class SearchTeamDTO {

    private Long teamSeq;
    private String teamName;
    private String sigungu;
    private String startDay;
    private String endDay;
    private String startTime;
    private String endTime;
    private Integer pageNo;

    /* 페이징 처리를 위한 VO */
    private PagerDTO pagerDTO;

    public SearchTeamDTO teamSeq (Long teamSeq) {
        this.teamSeq = teamSeq;
        return this;
    }

    public SearchTeamDTO teamName (String teamName) {
        this.teamName = teamName;
        return this;
    }

    public SearchTeamDTO sigungu (String sigungu) {
        this.sigungu = sigungu;
        return this;
    }

    public SearchTeamDTO startDay (String startDay) {
        this.startDay = startDay;
        return this;
    }

    public SearchTeamDTO endDay (String endDay) {
        this.endDay = endDay;
        return this;
    }

    public SearchTeamDTO startTime (String startTime) {
        this.startTime = startTime;
        return this;
    }

    public SearchTeamDTO endTime (String endTime) {
        this.endTime = endTime;
        return this;
    }

    public SearchTeamDTO pageNo (Integer pageNo) {
        this.pageNo = pageNo;
        return this;
    }

    public SearchTeamDTO pagerDTO (PagerDTO pagerDTO) {
        this.pagerDTO = pagerDTO;
        return this;
    }
}
