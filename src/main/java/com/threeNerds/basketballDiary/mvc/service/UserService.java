package com.threeNerds.basketballDiary.mvc.service;

import com.threeNerds.basketballDiary.mvc.domain.User;
import com.threeNerds.basketballDiary.mvc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
//@Transactional
public class UserService {

    private final UserRepository userRepository;

    /**
     *     private String userId;
     *     private String password;
     *     private Address address;
     *     private String email;
     *     private String gender;
     *     private double height;
     *     private double weight;
     *     private LocalDate regDate;
     *     private LocalDate updateDate;
     *     private String userRegYn;
     *     private String city;
     *     private Long sidoCod;
     *     private Long sggCod;
     */

    //@Transactional
    public User findUser(Long id) {
        Map<String, Object> retUser = userRepository.findUser(id);
        User user = new User();
        return user;
    }
    //@Transactional
    public void createMember(User user) {
        userRepository.saveUser(user);
    }
}
