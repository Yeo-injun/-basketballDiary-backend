package com.threeNerds.basketballDiary.mvc.user.controller.request;

import com.threeNerds.basketballDiary.mvc.user.service.dto.PasswordCommand;
import com.threeNerds.basketballDiary.session.SessionUser;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

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
