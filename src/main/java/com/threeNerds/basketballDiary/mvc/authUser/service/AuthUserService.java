package com.threeNerds.basketballDiary.mvc.authUser.service;

import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.error.DomainErrorType;
import com.threeNerds.basketballDiary.exception.error.SystemErrorType;
import com.threeNerds.basketballDiary.mvc.authUser.service.dto.MembershipCommand;
import com.threeNerds.basketballDiary.mvc.authUser.service.dto.PasswordCommand;
import com.threeNerds.basketballDiary.mvc.authUser.service.dto.ProfileCommand;
import com.threeNerds.basketballDiary.mvc.user.domain.User;
import com.threeNerds.basketballDiary.mvc.user.dto.UserDTO;
import com.threeNerds.basketballDiary.mvc.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AuthUserService {

    private final UserRepository userRepository;


    public void updatePassword( PasswordCommand command ) {
        User findUser = userRepository.findUser( command.getUserSeq() );

        if ( null == findUser ) {
            throw new CustomException( DomainErrorType.USER_NOT_FOUND );
        }

        if ( !findUser.checkAuthentication( command.getPrevPassword() ) ) {
            throw new CustomException( DomainErrorType.INCORRECT_PASSWORD );
        }

        userRepository.updatePassword( User.ofUpdate( command ) );
    }
}
