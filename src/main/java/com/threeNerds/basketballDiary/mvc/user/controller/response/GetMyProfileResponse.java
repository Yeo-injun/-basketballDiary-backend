package com.threeNerds.basketballDiary.mvc.user.controller.response;

import com.threeNerds.basketballDiary.mvc.user.dto.MyProfileDTO;
import lombok.Getter;

@Getter
public class GetMyProfileResponse {

    private final MyProfileDTO profile;

    public GetMyProfileResponse( MyProfileDTO profile ) {
        this.profile = profile;
    }
}
