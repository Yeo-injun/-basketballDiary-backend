package com.threeNerds.basketballDiary.mvc.team.service.dto;

import com.threeNerds.basketballDiary.auth.constant.AuthLevel;
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
        Map<Long, AuthLevel> authTeams;

        Result( Map< Long, AuthLevel > authTeams ) {
            this.authTeams = authTeams;
        }
    }

    public Result buildResult( Map<Long, AuthLevel > authTeams ) {
        return new Result( authTeams );
    }
}
