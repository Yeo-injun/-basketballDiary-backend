package com.threeNerds.basketballDiary.mvc.auth.controller.request;

import com.threeNerds.basketballDiary.mvc.auth.service.dto.LoginUserQuery;
import com.threeNerds.basketballDiary.mvc.game.service.dto.GameJoinPlayerCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class LoginRequest {

    @NotEmpty
    private String userId;
    @NotEmpty
    private String password;
    public LoginUserQuery toQuery() {
        return LoginUserQuery.builder()
                .userId(        this.userId )
                .password(      this.password )
                .build();
    }
}
