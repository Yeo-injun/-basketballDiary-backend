package com.threeNerds.basketballDiary.mvc.authUser.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MembershipCommand {
    private Long userSeq;
    private String plainPassword;
}
