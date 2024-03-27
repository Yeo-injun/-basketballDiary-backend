package com.threeNerds.basketballDiary.mvc.authUser.controller.request;

import com.threeNerds.basketballDiary.mvc.authUser.service.dto.PasswordCommand;
import com.threeNerds.basketballDiary.session.SessionUser;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
public class UpdatePasswordRequest {
    @NotEmpty
    private String prevPassword;
    @NotEmpty
    private String newPassword;

    public PasswordCommand toCommand( SessionUser userSession ) {
        return PasswordCommand.builder()
                .userSeq(       userSession.getUserSeq() )
                .userId(        userSession.getUserId() )
                .prevPassword(  this.prevPassword )
                .newPassword(   this.newPassword )
                .build();
    }
}
