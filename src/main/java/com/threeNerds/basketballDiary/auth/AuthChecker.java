package com.threeNerds.basketballDiary.auth;

import com.threeNerds.basketballDiary.auth.constant.AuthLevel;
import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.error.SystemErrorType;
import com.threeNerds.basketballDiary.utils.SessionUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 현재 세션의 권한정보를 관리하는 객체
 * -
 */
public class AuthChecker {
    /**
     * 요구되는 권한을 충족하는지 확인해야 하는 사용자의 권한
     * 팀에 대한 권한 정보를 관리하며, 팀Seq가 key이고, 해당 팀의 권한수준(Auth Level)이 Key의 Value으로 관리된다.
     */
    private final Map<Long, Long> teamAuthMap;
    /**
     * 요구되는 권한정보를 가지고 있는 어노테이션
     * - AuthChecker 생성시 해당 어노테이션 타입을 외부에서 주입한다.
     */
    private final Auth requiredAuth;

    AuthChecker( Map<Long, Long> teamAuthMap, Auth requiredAuth ) {
        this.teamAuthMap   = teamAuthMap;
        this.requiredAuth  = requiredAuth;
    }
    // 세션의 권한정보 객체 생성
    public static AuthChecker ofSession( Auth requiredAuth ) {
        return new AuthChecker( SessionUtil.getAuth(), requiredAuth );
    }

    // 권한체크
    public boolean checkAuth( HttpServletRequest request ) {
        // 요구되는 권한이 사용자 수준이면 추가 확인 없이 유효한 접근처리
        if ( AuthLevel.MEMBER == this.requiredAuth.level() ) {
            return true;
        }
        /*-------------------------------
         * 소속팀 권한 체크
         *-------------------------------*/
        final Map<String, String> pathVariables = (Map<String, String>) request.getAttribute( HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE );
        String queryStringTeamSeq               = pathVariables.get( "teamSeq" );
        // 소속팀 권한을 제한하는 URL에는 teamSeq 값이 URL에 존재해야 함.
        if ( !StringUtils.hasText( queryStringTeamSeq ) ) {
            throw new CustomException( SystemErrorType.ERROR_IN_TEAM_AUTH_CHECK );
        }
        Long userAuthLevel   = teamAuthMap.get( Long.parseLong( queryStringTeamSeq ) );
        // userAuthLevel이 null 이면 URL의 권한수준을 충족하지 못한 것으로 접근을 제한해야 함.
        if ( null == userAuthLevel ) {
            return false;
        }
        // 현재 나의 권한이 api의 권한보다 크거나 같을때 접근가능
        return userAuthLevel >= requiredAuth.level().getLevel();
    }
}
