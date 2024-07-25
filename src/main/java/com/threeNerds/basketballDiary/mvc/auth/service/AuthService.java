package com.threeNerds.basketballDiary.mvc.auth.service;

import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.error.DomainErrorType;
import com.threeNerds.basketballDiary.mvc.auth.service.dto.LoginUserQuery;
import com.threeNerds.basketballDiary.mvc.user.domain.User;

import com.threeNerds.basketballDiary.mvc.auth.service.dto.LoginUserDTO;

import com.threeNerds.basketballDiary.mvc.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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

    /**
     * 입력정보로 로그인 정보 조회 및 return
     * @throw DomainErrorType.INCORRECT_LOGIN_INFO 로그인 정보가 일치하지 않을 경우 해당 오류메세지 throw
     **/
    public LoginUserQuery.Result login( LoginUserQuery query ) {
        String userId        = query.getUserId();
        String plainPassword = query.getPassword();

        log.info("Check User id = {}", userId);
        User findUser = userRepository.findUserByUserId( userId );

        if ( findUser == null ) {
            throw new CustomException( DomainErrorType.INCORRECT_LOGIN_INFO );
        }

        /** 입력 ID와 비밀번호로 인증 수행 */
        if ( !findUser.checkAuthentication( plainPassword ) ) {
            throw new CustomException( DomainErrorType.INCORRECT_LOGIN_INFO );
        }
        return query.buildResult( findUser.getUserSeq() );
    }
}
