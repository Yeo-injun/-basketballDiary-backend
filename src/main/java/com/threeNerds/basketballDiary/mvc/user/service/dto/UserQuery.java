package com.threeNerds.basketballDiary.mvc.user.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserQuery {

    @NotNull
    private Long teamSeq;
    private String userName;
    private String email;

}
