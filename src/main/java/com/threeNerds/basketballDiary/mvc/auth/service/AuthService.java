package com.threeNerds.basketballDiary.mvc.auth.service;

import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.error.DomainErrorType;
import com.threeNerds.basketballDiary.mvc.auth.controller.request.CreateUserRequest;
import com.threeNerds.basketballDiary.mvc.user.domain.User;
import com.threeNerds.basketballDiary.mvc.team.dto.TeamAuthDTO;

import com.threeNerds.basketballDiary.mvc.auth.dto.LoginUserDTO;

import com.threeNerds.basketballDiary.mvc.myTeam.repository.TeamMemberRepository;
import com.threeNerds.basketballDiary.mvc.user.repository.UserRepository;
import com.threeNerds.basketballDiary.session.SessionUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * App의 모든 인증과 권한과 관련된 업무를 수행하는 Service
 * @author 책임자 작성
 *
 * issue and history
 * <pre>
 * 2022.12.24 여인준 : AuthUserService와 UserService에서 인증 및 권한에 대한 로직만 따로 추출하여 AuthService생성
 * </pre>
 */

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final TeamMemberRepository teamMemberRepository;

    public boolean checkDuplicationUserId( String userId ) {
        return !isUserIdAvailable( userId );
    }

    public void createMember( CreateUserRequest request ) {
        if ( isUserIdAvailable( request.getUserId() ) ) {
            User user = User.createForRegistration( request );
            userRepository.saveUser( user );
            return;
        }

        throw new CustomException( DomainErrorType.NOT_AVAILABLE_USER_ID );
    }

    private boolean isUserIdAvailable( String userId ) {
        return null == userRepository.findUserByUserId( userId );
    }

    // TODO 세션 정보 생성은 controller에서 책임을 가지고 있음. SessionUser는 Service에서 참조할 수 없음
    public SessionUser login(LoginUserDTO loginUserDTO) {
        String userId        = loginUserDTO.getUserId();
        String plainPassword = loginUserDTO.getPassword();

        log.info("Check User id = {}", userId);
        User findUser = userRepository.findUserByUserId(userId);

        if (findUser == null) {
            throw new CustomException(DomainErrorType.USER_NOT_FOUND);
        }

        /** 로그인가능여부 체크 */
        findUser.isAuthUser(userId, plainPassword);
        SessionUser sessionUser = SessionUser.create(findUser);

        /**만약 소속되어 있는 팀이 존재하지 않는다면 권한 정보 없이 return */
        Long myTeamCount = teamMemberRepository.findMyTeamCount(findUser.getUserSeq());
        boolean hasNoTeams = myTeamCount <= 0;
        if(hasNoTeams) {
            return sessionUser;
        }

        /** 권한정보 생성후 SesstionUser객체 return */
        List<TeamAuthDTO> authList = userRepository.findAuthList(findUser);
        return sessionUser.updateAuthority(authList);
    }
}
