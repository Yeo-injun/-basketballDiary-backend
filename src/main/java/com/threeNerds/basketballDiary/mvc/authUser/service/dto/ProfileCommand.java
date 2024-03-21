package com.threeNerds.basketballDiary.mvc.authUser.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileCommand {
    private Long userSeq;
    private String userName;
    private String birthYmd;
    private String gender;
    private String email;
    private Double height;
    private Double weight;
    private String sidoCode;
    private String sigunguCode;
    private String positionCode;
    private String roadAddress;
}
