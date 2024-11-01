package com.threeNerds.basketballDiary.auth.validation.game;

import com.threeNerds.basketballDiary.auth.AuthorizationChecker;
import com.threeNerds.basketballDiary.auth.AuthorizationStatus;
import com.threeNerds.basketballDiary.exception.error.ErrorMessageType;
import com.threeNerds.basketballDiary.exception.error.SystemErrorType;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 현재 세션의 권한정보를 관리하는 객체
 * -
 */
public class GameAuthChecker implements AuthorizationChecker {
    /**
     * 요구되는 권한을 충족하는지 확인해야 하는 사용자의 권한정보 관리 필드
     * - authGameMap은 경기에 대한 권한 정보를 Map으로 관리. cf. key : 경기Seq / value : 해당 경기의 권한수준(Auth Level)
     */
    private final Map< String, String > userAuthRegistry;

    /**
     * 요구되는 권한정보를 가지고 있는 어노테이션
     * - AuthChecker 생성시 해당 어노테이션 타입을 외부에서 주입한다.
     */
    private final GameAuth requiredAuth;

    public GameAuthChecker( GameAuth requiredAuth, Map< String, String > sessionGameAuth ) {
        this.requiredAuth       = requiredAuth;
        this.userAuthRegistry   = sessionGameAuth;
    }

    // 권한체크
    @Override
    public AuthorizationStatus checkAuthStatus( HttpServletRequest request ) {
        final Map<String, String> pathVariables = (Map<String, String>) request.getAttribute( HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE );
        final String targetGameSeq              = pathVariables.get( "gameSeq" );
        // 소속팀 권한을 제한하는 URL에는 teamSeq 값이 URL에 존재해야 함.
        if ( !StringUtils.hasText( targetGameSeq ) ) {
            return buildStatusUnavailableAuthCheck();
        }
        return buildStatusByAuthCheck( userAuthRegistry.get( targetGameSeq ) );
    }

    private AuthorizationStatus buildStatusUnavailableAuthCheck() {
        return new AuthorizationStatus() {
            @Override
            public boolean isPermission() {
                return false;
            }

            @Override
            public ErrorMessageType getErrorMessage() {
                return SystemErrorType.ERROR_IN_TEAM_AUTH_CHECK;
            }
        };
    }

    private AuthorizationStatus buildStatusByAuthCheck( String userAuthLevel ) {
        GameAuth userGameAuth = GameAuth.ofLevel( userAuthLevel );
        boolean isPermission = this.requiredAuth.isPermissionGranted( userGameAuth );
        return new AuthorizationStatus() {
            @Override
            public boolean isPermission() {
                return isPermission;
            }

            @Override
            public ErrorMessageType getErrorMessage() {
                return SystemErrorType.UNAUTHORIZED_ACCESS;
            }
        };
    }
}
