package com.threeNerds.basketballDiary.mvc.user.controller.response;

import com.threeNerds.basketballDiary.mvc.user.mapper.dto.MyProfileDTO;
import lombok.Getter;

@Getter
public class GetMyProfileResponse {

    private final MyProfileDTO profile;

    public GetMyProfileResponse( MyProfileDTO profile ) {
        this.profile = profile;
    }
}
