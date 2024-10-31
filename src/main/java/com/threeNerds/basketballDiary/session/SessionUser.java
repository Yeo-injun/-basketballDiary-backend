package com.threeNerds.basketballDiary.session;

import com.threeNerds.basketballDiary.auth.validation.game.GameAuth;
import lombok.Getter;

import java.io.Serializable;
import java.util.Map;

@Getter
public class SessionUser implements Serializable {

    private final Long userSeq;
    private final String userId;

    /* 이하 세션이 생성된 이후에도 속성들은 값이 변경될 수 있음 - 팀생성, 경기 생성 등. */
    private Map< Long, Integer > authTeams;    // 팀을 기준으로 권한수준 관리
    private Map< Long, Integer > authGames;    // 경기를 기준으로 권한수준 관리

    private SessionUser(
        Long userSeq, String userId,
        Map< Long, Integer > authTeams,
        Map< Long, Integer > authGames
    ) {
        this.userSeq = userSeq;
        this.userId = userId;
        this.authTeams = authTeams;
        this.authGames = authGames;
   }



    public static SessionUser createWithAuth(
            Long userSeq, String userId,
            Map< Long, Integer > authTeams,       // 소속팀을 기준으로 권한수준을 관리
            Map< Long, Integer > authGames        // 경기를 기준으로 권한수준 관리
    ) {
        return new SessionUser( userSeq, userId, authTeams, authGames );
    }

    public void setAuthTeams( Map< Long, Integer > authTeams ) {
        this.authTeams = authTeams;
    }

    public void addGameCreatorAuth( Long gameSeq ) {
        this.authGames.put( gameSeq, GameAuth.GAME_CREATOR.getLevel() );
    }

}
