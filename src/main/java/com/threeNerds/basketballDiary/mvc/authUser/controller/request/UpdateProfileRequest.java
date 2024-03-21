package com.threeNerds.basketballDiary.mvc.authUser.controller.request;

import com.threeNerds.basketballDiary.mvc.authUser.service.dto.ProfileCommand;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
public class UpdateProfileRequest {

    @NotEmpty
    private String userName;
//    @NotEmpty TODO 임시로 주석처리 >> 화면에서 개발되면 주석 해제 예정
    private String birthYmd;
    @NotEmpty
    private String gender;
    @Email
    @NotEmpty
    private String email;
    @NotNull
    private Double height;
    private Double weight;
    @NotEmpty
    private String sidoCode;
    @NotEmpty
    private String sigunguCode;
    private String positionCode;
    private String roadAddress;

    public ProfileCommand toCommand( Long userSeq ) {
        return ProfileCommand.builder()
                .userSeq(       userSeq )
                .userName(      this.userName )
                .birthYmd(      this.birthYmd )
                .gender(        this.gender )
                .email(         this.email )
                .height(        this.height )
                .weight(        this.weight )
                .sidoCode(      this.sidoCode )
                .sigunguCode(   this.sigunguCode )
                .positionCode(  this.positionCode )
                .roadAddress(   this.roadAddress )
                .build();
    }
}
