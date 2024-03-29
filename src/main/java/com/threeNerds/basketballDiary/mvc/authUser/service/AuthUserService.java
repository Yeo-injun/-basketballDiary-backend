package com.threeNerds.basketballDiary.mvc.authUser.service;

import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.error.DomainErrorType;
import com.threeNerds.basketballDiary.exception.error.SystemErrorType;
import com.threeNerds.basketballDiary.mvc.authUser.controller.request.UpdateProfileRequest;
import com.threeNerds.basketballDiary.mvc.authUser.service.dto.ProfileCommand;
import com.threeNerds.basketballDiary.mvc.user.domain.User;
import com.threeNerds.basketballDiary.mvc.authUser.dto.PasswordUpdateDTO;
import com.threeNerds.basketballDiary.mvc.authUser.dto.UpdateUserDTO;
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

    public UserDTO getProfile( Long userSeq ) {
        User findUser = Optional.ofNullable( userRepository.findUser( userSeq ) )
                                .orElseThrow( ()-> new CustomException( DomainErrorType.USER_NOT_FOUND ) );
        return UserDTO.getInstance(findUser);
    }


    public void updateProfile( ProfileCommand profile ) {
        boolean isSuccessUpdateProfile = userRepository.updateProfile( User.ofUpdate( profile ) ) == 1;
        if ( !isSuccessUpdateProfile ) {
            throw new CustomException( SystemErrorType.NOT_FOUND_USER_FOR_UPDATE );
        }
    }

    public void deleteUser(String id) {
        userRepository.deleteUser(id);
    }

    public void updatePassword( PasswordUpdateDTO passwordUpdateDTO ) {
        User findUser = Optional.ofNullable(userRepository.findUser(passwordUpdateDTO.getUserSeq()))
                .orElseThrow(()-> new CustomException(DomainErrorType.USER_NOT_FOUND));

        String prevPassword = Optional.ofNullable(passwordUpdateDTO.getPrevPassword())
                .orElseThrow(()-> new CustomException( DomainErrorType.INCORRECT_PASSWORD ));

        if ( !prevPassword.equals( findUser.getPassword() ) ) {
            throw new CustomException( DomainErrorType.INCORRECT_PASSWORD );
        }
        userRepository.updatePassword(passwordUpdateDTO);
    }
}
