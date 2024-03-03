package com.threeNerds.basketballDiary.mvc.myTeam.service.dto;

import com.threeNerds.basketballDiary.auth.constant.AuthLevel;
import lombok.Getter;

import java.util.Map;
import java.util.Set;

@Getter
public class TeamAuthDTO {

    Long userSeq;
    // 소속팀을 기준으로 권한수준을 관리
    Map<Long, AuthLevel> authTeams;

    private TeamAuthDTO ( Long userSeq, Map< Long, AuthLevel > authTeams ) {
        this.userSeq    = userSeq;
        this.authTeams  = authTeams;
    }

    public static TeamAuthDTO of( Long userSeq ) {
        return new TeamAuthDTO( userSeq, null );
    }
    // 소속 팀의 권한수준 정보를 생성
    public static TeamAuthDTO ofJoinTeam( Long userSeq, Map<Long, AuthLevel> authTeams ) {
        return new TeamAuthDTO( userSeq, authTeams );
    }
}
