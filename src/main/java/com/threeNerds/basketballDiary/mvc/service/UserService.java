package com.threeNerds.basketballDiary.mvc.service;

import com.threeNerds.basketballDiary.mvc.domain.User;
import com.threeNerds.basketballDiary.mvc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User findUser(Long id) {
        return userRepository.findUser(id);
    }
    @Transactional
    public Long createMember(User user) {
        return userRepository.saveUser(user);
    }
    @Transactional
    public Long updateUser(User user){
        String aLong = userRepository.updateUser(user);
        return 1L;
    }
}
