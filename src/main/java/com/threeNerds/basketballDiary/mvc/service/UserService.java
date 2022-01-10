package com.threeNerds.basketballDiary.mvc.service;

import com.threeNerds.basketballDiary.mvc.domain.User;
import com.threeNerds.basketballDiary.mvc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
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

    public User findUser(Long id) {
        Map<String, Object> retUser = userRepository.findUser(id);
        User user = new User();
        return user;
    }
    public void createMember(User user) {
        Map<String,Object> sqlParam = new HashMap<>();
        sqlParam.put("userId",user.getUserId());
        sqlParam.put("password",user.getPassword());
        sqlParam.put("userName",user.getUserName());
        sqlParam.put("email",user.getEmail());
        sqlParam.put("gender",user.getGender());
        sqlParam.put("height",user.getHeight());
        sqlParam.put("weight",user.getWeight());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM_DD");
        sqlParam.put("regDate", user.getRegDate().format(formatter));
        sqlParam.put("updateDate",user.getUpdateDate().format(formatter));
        sqlParam.put("userRegYn",user.getUserRegYn());

        sqlParam.put("sidoCode",user.getSidoCode());
        sqlParam.put("sigunguCode",user.getSigunguCode());
        sqlParam.put("positionCode",user.getPositionCode());

        userRepository.saveUser(sqlParam);
    }
}
