package com.threeNerds.basketballDiary.mvc.domain;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Team {

    private String leaderId;

    private String teamName;

    private String hometown;

    private String introduction;

    private LocalDate foundationYmd;

    private LocalDate regDate;

    private LocalDate updateDate;

    private String sidoCode;

    private String sigunguCode;
}
