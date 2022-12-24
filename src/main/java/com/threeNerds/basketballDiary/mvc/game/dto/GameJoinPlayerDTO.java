package com.threeNerds.basketballDiary.mvc.game.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class GameJoinPlayerDTO {

    private Long gameJoinPlayerSeq;     // 게임참가선수Seq
    private Long gameJoinTeamSeq;       // 게임참가팀Seq
    private String playerTypeCode;      // 선수유형코드
    private String playerTypeCodeName;  // 선수유형코드명

    private Long userSeq;               // 사용자Seq
    private String name;                // 이름
    private String backNumber;          // 등번호
    private String positionCode;        // 포지션코드
    private String positionCodeName;    // 포지션코드명

    private String email;               // 이메일
    
}
