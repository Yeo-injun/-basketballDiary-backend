package com.threeNerds.basketballDiary.mvc.myTeam.dto.modifyMyTeamProfile.request;

import lombok.Getter;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;


@Getter
public class ModifyMyTeamProfileRequest {
    private Long userSeq;
    private Long teamSeq;
    private String backNumber;
    private MultipartFile imageFile;

    public ModifyMyTeamProfileRequest( Long userSeq, Long teamSeq, String backNumber, MultipartFile imageFile ) {
        this.userSeq    = userSeq;
        this.teamSeq    = teamSeq;
        this.backNumber = backNumber;
        this.imageFile  = imageFile;
    }
}
