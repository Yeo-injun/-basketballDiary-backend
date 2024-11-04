package com.threeNerds.basketballDiary.auth;

import com.threeNerds.basketballDiary.auth.exception.AuthCheckException;
import com.threeNerds.basketballDiary.auth.exception.AuthorizationException;
import com.threeNerds.basketballDiary.auth.exception.NotAllowedAuthException;
import com.threeNerds.basketballDiary.auth.validation.type.TeamAuth;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 현재 세션의 권한정보를 관리하는 객체
 * -
 */
public class TeamAuthChecker implements AuthorizationChecker {
    /**
     * 요구되는 권한을 충족하는지 확인해야 하는 사용자의 권한정보 관리 필드
     * - 팀에 대한 권한 정보를 Map으로 관리. cf. key : 팀Seq / value : 해당 팀의 권한수준(Auth Level)
     */
    private final Map< Long, Integer > userAuthRegistry;

    /**
     * 요구되는 권한정보를 가지고 있는 어노테이션
     * - AuthChecker 생성시 해당 어노테이션 타입을 외부에서 주입한다.
     */
    private final TeamAuth requiredAuth;

    public TeamAuthChecker( TeamAuth requiredAuth, Map< Long, Integer > sessionGameAuth ) {
        this.requiredAuth       = requiredAuth;
        this.userAuthRegistry   = sessionGameAuth;
    }


    // 권한체크
    public AuthorizationStatus checkAuthStatus( HttpServletRequest request ) {
        final Map<String, String> pathVariables = (Map<String, String>) request.getAttribute( HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE );
        String queryStringTeamSeq               = pathVariables.get( "teamSeq" );
        // 소속팀 권한을 제한하는 URL에는 teamSeq 값이 URL에 존재해야 함.
        if ( !StringUtils.hasText( queryStringTeamSeq ) ) {
            return buildStatusUnavailableAuthCheck();
        }
        return buildStatusByAuthCheck( userAuthRegistry.get( Long.parseLong( queryStringTeamSeq ) ) );
    }

    private AuthorizationStatus buildStatusUnavailableAuthCheck() {
        return new AuthorizationStatus() {
            @Override
            public boolean isPermission() {
                return false;
            }

            @Override
            public AuthorizationException getException() {
                return new AuthCheckException( "요청에 대해 팀 정보를 식별할 수 없어 권한을 체크할 수 없습니다." );
            }
        };
    }

    private AuthorizationStatus buildStatusByAuthCheck( Integer userAuthLevel ) {
        TeamAuth userTeamAuth = TeamAuth.ofLevel( String.valueOf( userAuthLevel ) );
        boolean isPermission = this.requiredAuth.isPermissionGranted( userTeamAuth );
        return new AuthorizationStatus() {
            @Override
            public boolean isPermission() {
                return isPermission;
            }

            @Override
            public AuthorizationException getException() {
                return new NotAllowedAuthException( "해당 요청을 처리할 수 있는 팀 권한을 가지고 있지 않습니다." );
            }
        };
    }

}
