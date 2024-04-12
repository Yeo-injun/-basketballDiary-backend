package com.threeNerds.basketballDiary.mvc.user.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PasswordCommand {
    private Long userSeq;
    private String userId;
    private String prevPassword;
    private String newPassword;
}
