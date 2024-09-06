package com.threeNerds.basketballDiary.mvc.myTeam.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileCommand {
    private Long userSeq;
    private Long teamSeq;
    private String backNumber;
    private MultipartFile profileImage;
}
