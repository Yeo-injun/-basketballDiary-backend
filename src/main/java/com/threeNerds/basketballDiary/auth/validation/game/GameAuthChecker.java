package com.threeNerds.basketballDiary.auth.validation.game;

import com.threeNerds.basketballDiary.auth.AuthorizationChecker;
import com.threeNerds.basketballDiary.auth.AuthorizationStatus;
import com.threeNerds.basketballDiary.exception.error.ErrorMessageType;
import com.threeNerds.basketballDiary.session.SessionUser;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 현재 세션의 권한정보를 관리하는 객체
 * -
 */
public class GameAuthChecker implements AuthorizationChecker {
    /**
     * 요구되는 권한을 충족하는지 확인해야 하는 사용자의 권한정보 관리 필드
     * - authTeamMap은 팀에 대한 권한 정보를 Map으로 관리. cf. key : 팀Seq / value : 해당 팀의 권한수준(Auth Level)
     * - authGameMap은 경기에 대한 권한 정보를 Map으로 관리. cf. key : 경기Seq / value : 해당 경기의 권한수준(Auth Level)
     */
    private final Map< Long, Integer > authTeamMap;

    /**
     * 요구되는 권한정보를 가지고 있는 어노테이션
     * - AuthChecker 생성시 해당 어노테이션 타입을 외부에서 주입한다.
     */
    private final GameAuth requiredAuth;

    public GameAuthChecker( GameAuth requiredAuth, SessionUser userSession ) {
        this.requiredAuth  = requiredAuth;
        this.authTeamMap   = userSession.getAuthGames();
    }


    // 권한체크
    @Override
    public AuthorizationStatus checkAuthStatus(HttpServletRequest request) {
        return new AuthorizationStatus() {
            @Override
            public boolean isPermission() {
                return true;
            }

            @Override
            public ErrorMessageType getErrorMessage() {
                return null;
            }
        };
    }
}
