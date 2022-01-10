package com.threeNerds.basketballDiary.mvc.controller;

import com.threeNerds.basketballDiary.mvc.domain.User;
import com.threeNerds.basketballDiary.mvc.dto.UserDTO;
import com.threeNerds.basketballDiary.mvc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users/new")
    public String createForm(){
        return "members/createForm";
    }
    @PostMapping("/users/new")
    public String create(@RequestBody @Valid UserDTO userDTO){
        User user = new User();
        user.setUserId(userDTO.getUserId());
        user.setPassword(userDTO.getPassword());
        user.setUserName(userDTO.getUserName());
        user.setEmail(userDTO.getEmail());
        user.setGender(userDTO.getGender());
        user.setWeight(userDTO.getWeight());

        LocalDate today = LocalDate.now();
        user.setRegDate(today);
        user.setUpdateDate(today);
        user.setUserRegYn("Y");

        user.setSidoCode(userDTO.getSidoCode());
        user.setSigunguCode(userDTO.getSigunguCode());
        user.setPositionCode(userDTO.getPositionCode());

        userService.createMember(user);
        return "/";
    }
    @PatchMapping("/members/modify/{id}")
    public String modify(@PathVariable Long id,@RequestBody @Valid UserDTO userDTO){
        User user = userService.findUser(id);

        return "/";
    }

}
