package com.threeNerds.basketballDiary.mvc.team.service.dto;

import lombok.Getter;

import java.util.Map;

@Getter
public class TeamAuthDTO {

    Long userSeq;
    // 소속팀을 기준으로 권한수준을 관리
    Map<Long, Integer> authTeams;

    private TeamAuthDTO ( Long userSeq, Map< Long, Integer > authTeams ) {
        this.userSeq    = userSeq;
        this.authTeams  = authTeams;
    }

    public static TeamAuthDTO of( Long userSeq ) {
        return new TeamAuthDTO( userSeq, null );
    }
    // 소속 팀의 권한수준 정보를 생성
    public static TeamAuthDTO ofJoinTeam( Long userSeq, Map<Long, Integer> authTeams ) {
        return new TeamAuthDTO( userSeq, authTeams );
    }
}
