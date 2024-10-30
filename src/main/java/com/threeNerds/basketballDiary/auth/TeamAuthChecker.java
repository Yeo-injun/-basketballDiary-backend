package com.threeNerds.basketballDiary.auth;

import com.threeNerds.basketballDiary.auth.constant.AuthLevel;
import com.threeNerds.basketballDiary.auth.type.TeamAuth;
import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.error.SystemErrorType;
import com.threeNerds.basketballDiary.session.SessionUser;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 현재 세션의 권한정보를 관리하는 객체
 * -
 */
public class TeamAuthChecker {
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
    private final TeamAuth requiredAuth;

    public TeamAuthChecker( TeamAuth requiredAuth, SessionUser userSession ) {
        this.requiredAuth  = requiredAuth;
        this.authTeamMap   = userSession.getAuthTeams();
    }


    // 권한체크
    public boolean checkAuth( HttpServletRequest request ) {
        // 요구되는 권한이 일반회원 수준이면 유효한 접근처리 ( 로그인을 했다는 전제때문에 )
        final Map<String, String> pathVariables = (Map<String, String>) request.getAttribute( HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE );
        String queryStringTeamSeq               = pathVariables.get( "teamSeq" );
        // 소속팀 권한을 제한하는 URL에는 teamSeq 값이 URL에 존재해야 함.
        if ( !StringUtils.hasText( queryStringTeamSeq ) ) {
            throw new CustomException( SystemErrorType.ERROR_IN_TEAM_AUTH_CHECK );
        }
        Integer userAuthLevel = authTeamMap.get( Long.parseLong( queryStringTeamSeq ) );
        return this.requiredAuth.isPermissionGranted( userAuthLevel );
    }

}
