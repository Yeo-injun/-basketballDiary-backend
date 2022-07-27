package com.threeNerds.basketballDiary.mvc.dto.myTeam;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class MyTeamProfileDTO {

    private String backNumber;

    private MultipartFile imageFile;
}
