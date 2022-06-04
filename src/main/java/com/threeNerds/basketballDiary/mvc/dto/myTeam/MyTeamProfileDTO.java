package com.threeNerds.basketballDiary.mvc.dto.myTeam;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class MyTeamProfileDTO {
    private String backNumber;

    private MultipartFile imageFile;
}
