package com.threeNerds.basketballDiary.mvc.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
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
