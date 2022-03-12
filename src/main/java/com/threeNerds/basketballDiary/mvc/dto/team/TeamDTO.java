package com.threeNerds.basketballDiary.mvc.dto.team;

import lombok.Getter;
import lombok.Setter;

/**
 * @Setter 삭제, @Build 추가
 */
@Getter @Setter
public class TeamDTO {

    private String leaderId;

    private String teamName;

    private String hometown;

    private String introduction;

    private String sidoCode;

    private String sigunguCode;
}

