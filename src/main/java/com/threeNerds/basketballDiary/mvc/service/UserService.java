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
    public Long findSeq(String userId){
        return userRepository.findSeq(userId);
    }
    @Transactional
    public User findUser(Long id) {
        return userRepository.findUser(id);
    }
    @Transactional
    public void createMember(User user) {
        userRepository.saveUser(user);
    }
    @Transactional
    public void updateUser(User user){
        userRepository.updateUser(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteUser(id);
    }
}
