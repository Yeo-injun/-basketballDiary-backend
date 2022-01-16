package com.threeNerds.basketballDiary.mvc.service;

import com.threeNerds.basketballDiary.mvc.controller.LoginController;
import com.threeNerds.basketballDiary.mvc.domain.User;
import com.threeNerds.basketballDiary.mvc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class LoginService {

    private final UserRepository userRepository;

    //@Transactional
    public Optional<User> login(LoginController.LoginUserRequest loginUserRequest){
        User user = userRepository.loginFindUser(loginUserRequest);
        log.info("User id={}",user.getUserId());
        //null 인지 null이 아닌지 확신이 들지 않을때 사용 : ofNullable(null 이면 Optional.empty() 반환)
        return Optional.ofNullable(user);
    }
}
