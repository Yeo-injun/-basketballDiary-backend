package com.threeNerds.basketballDiary.mvc.game.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OppenentTeamQuery {

    String sidoCode;    /*시도코드*/
    String teamName;    /*팀이름*/
    String leaderName;  /*리더이름*/
    Integer pageNo;     /* 페이징 : 현재 페이지 */

}
