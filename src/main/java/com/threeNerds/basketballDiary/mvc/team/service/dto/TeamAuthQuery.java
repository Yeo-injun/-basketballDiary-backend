package com.threeNerds.basketballDiary.mvc.team.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamAuthQuery {

    Long userSeq;

    @Getter
    public class Result {
        // 소속팀을 기준으로 권한수준을 관리
        Map<Long, Integer> authTeams;

        Result( Map< Long, Integer > authTeams ) {
            this.authTeams = authTeams;
        }
    }

    public Result buildResult( Map<Long, Integer > authTeams ) {
        return new Result( authTeams );
    }
}
