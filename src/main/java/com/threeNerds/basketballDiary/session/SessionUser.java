package com.threeNerds.basketballDiary.session;

import com.threeNerds.basketballDiary.auth.constant.AuthLevel;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class SessionUser {

    private final Long userSeq;
    private final String userId;

    /* 이하 세션이 생성된 이후에도 속성들은 값이 변경될 수 있음 - 팀생성, 경기 생성 등. */
    private Map< Long, Integer > authTeams;    // 팀을 기준으로 권한수준 관리
    private Map< Long, Integer > authGames;    // 경기를 기준으로 권한수준 관리

    private SessionUser(
        Long userSeq, String userId,
        Map< Long, AuthLevel > authTeams,
        Map< Long, AuthLevel > authGames
    ) {
        this.userSeq = userSeq;
        this.userId = userId;
        this.authTeams = convertAuthLevelToInt( authTeams );
        this.authGames = convertAuthLevelToInt( authGames );
   }

   // AuthLevel 타입을 정수형으로 변환하여 권한Map을 생성
   private Map< Long, Integer > convertAuthLevelToInt( Map<Long, AuthLevel > authMaps ) {
       Map< Long, Integer > resultAuthMap = new HashMap<>();
       if ( null == authMaps ) {
           return resultAuthMap;
       }
       authMaps.forEach( ( key, authLevel ) -> {
           resultAuthMap.put( key, authLevel.getLevel() );
       } );
       return resultAuthMap;
   }

    public static SessionUser createWithAuth(
            Long userSeq, String userId,
            Map< Long, AuthLevel > authTeams,       // 소속팀을 기준으로 권한수준을 관리
            Map< Long, AuthLevel > authGames        // 경기를 기준으로 권한수준 관리
    ) {
        return new SessionUser( userSeq, userId, authTeams, authGames );
    }

    public void setAuthTeams( Map< Long, AuthLevel > authTeams ) {
        this.authTeams = convertAuthLevelToInt( authTeams );
    }

    public void addGameCreatorAuth( Long gameSeq ) {
        this.authGames.put( gameSeq, AuthLevel.GAME_CREATOR.getLevel() );
    }

}
