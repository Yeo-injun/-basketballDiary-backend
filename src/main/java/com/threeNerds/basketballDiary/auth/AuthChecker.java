package com.threeNerds.basketballDiary.auth;

import com.threeNerds.basketballDiary.auth.constant.AuthLevel;
import com.threeNerds.basketballDiary.auth.constant.AuthType;
import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.error.SystemErrorType;
import com.threeNerds.basketballDiary.session.SessionUser;
import com.threeNerds.basketballDiary.utils.SessionUtil;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;

/**
 * 현재 세션의 권한정보를 관리하는 객체
 * -
 */
public class AuthChecker {
    /**
     * 요구되는 권한을 충족하는지 확인해야 하는 사용자의 권한정보 관리 필드
     * - authTeamMap은 팀에 대한 권한 정보를 Map으로 관리. cf. key : 팀Seq / value : 해당 팀의 권한수준(Auth Level)
     * - authGameMap은 경기에 대한 권한 정보를 Map으로 관리. cf. key : 경기Seq / value : 해당 경기의 권한수준(Auth Level)
     */
    private final Map< Long, Integer > authTeamMap;
    private final Map< Long, Integer > authGameMap;

    /**
     * 요구되는 권한정보를 가지고 있는 어노테이션
     * - AuthChecker 생성시 해당 어노테이션 타입을 외부에서 주입한다.
     */
    private final Auth requiredAuth;

    // 외부에서는 사용할 수 없는 생성자. 객체를 생성하기 위해서는 정적 팩토리 메소드를 사용해야 함.
    private AuthChecker( Auth requiredAuth, SessionUser userSession ) {
        this.requiredAuth  = requiredAuth;
        this.authTeamMap   = userSession.getAuthTeams();
        this.authGameMap   = userSession.getAuthGames();
    }
    // 세션의 권한정보 객체 생성
    public static AuthChecker ofSession( Auth requiredAuth ) {
        // 로그인한 상태임을 가정
        if ( !SessionUtil.isLogin() ) {
            throw new CustomException( SystemErrorType.LOGIN_REQUIRED );
        }
        return new AuthChecker( requiredAuth, SessionUtil.getSessionUser() );
    }

    // 권한체크
    public boolean checkAuth( HttpServletRequest request ) {
        // 요구되는 권한이 일반회원 수준이면 유효한 접근처리 ( 로그인을 했다는 전제때문에 )
        if  ( AuthLevel.MEMBER == this.requiredAuth.level() ) {
            return true;
        }
        switch ( requiredAuth.type() ) {
            case AuthType.TEAM          : return checkTeamAuth( request );
            case AuthType.GAME_RECORD   : return checkGameAuth( request );
            default:    return true; // TODO 임시처리... 기본값 및 로직 처리를 어떻게 할지 고민
        }
    }

    /*-------------------------------
     * 소속팀 권한 체크
     *-------------------------------*/
    private boolean checkTeamAuth( HttpServletRequest request ) {
        final Map<String, String> pathVariables = (Map<String, String>) request.getAttribute( HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE );
        String queryStringTeamSeq               = pathVariables.get( "teamSeq" );
        // 소속팀 권한을 제한하는 URL에는 teamSeq 값이 URL에 존재해야 함.
        if ( !StringUtils.hasText( queryStringTeamSeq ) ) {
            throw new CustomException( SystemErrorType.ERROR_IN_TEAM_AUTH_CHECK );
        }
        Integer userAuthLevel = authTeamMap.get( Long.parseLong( queryStringTeamSeq ) );
        // userAuthLevel이 null 이면 URL의 권한수준을 충족하지 못한 것으로 접근을 제한해야 함.
        if ( null == userAuthLevel ) {
            return false;
        }
        // 현재 나의 권한이 api의 권한보다 크거나 같을때 접근가능
        return userAuthLevel >= requiredAuth.level().getLevel();
    }

    /*-------------------------------
     * 경기기록 권한 체크
     *-------------------------------*/
    private boolean checkGameAuth( HttpServletRequest request ) {
    // TODO 구현 요망
        return true;
    }
}
