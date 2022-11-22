package com.threeNerds.basketballDiary.mvc.game.domain;

import com.threeNerds.basketballDiary.constant.code.HomeAwayCode;
import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.Error;
import com.threeNerds.basketballDiary.mvc.domain.Team;
import com.threeNerds.basketballDiary.mvc.domain.User;
import com.threeNerds.basketballDiary.mvc.game.dto.GameJoinPlayerDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameJoinPlayer {

    private Long gameJoinPlayerSeq;     // 게임참가선수Seq
    private Long gameJoinTeamSeq;       // 게임참가팀Seq
    private String playerTypeCode;      // 선수유형코드
    private Long userSeq;               // 사용자Seq
    private String name;                // 이름
    private String backNumber;          // 등번호
    private String positionCode;        // 포지션코드
    private String email;               // 이메일

    public static GameJoinPlayer create(Long gameJoinTeamSeq, GameJoinPlayerDTO joinPlayerDTO)
    {
        return GameJoinPlayer.builder()
                .gameJoinTeamSeq(gameJoinTeamSeq)
                .playerTypeCode(joinPlayerDTO.getPlayerTypeCode())
                .name(joinPlayerDTO.getName())
                .backNumber(joinPlayerDTO.getBackNumber())
                .positionCode(joinPlayerDTO.getPositionCode())
                .email(joinPlayerDTO.getEmail())
                .build();
    }

    public static GameJoinPlayer create(
            Long gameJoinTeamSeq, String playerTypeCode,
            String backNumber, User user
    ) {
        return create(gameJoinTeamSeq, playerTypeCode, user.getUserSeq(), user.getUserName(), backNumber, user.getPositionCode(), user.getEmail());
    }

    private static GameJoinPlayer create(
            Long gameJoinTeamSeq, String playerTypeCode, Long userSeq, String name,
            String backNumber, String positionCode, String email
    ) {
        return GameJoinPlayer.builder()
                .gameJoinTeamSeq(gameJoinTeamSeq)
                .playerTypeCode(playerTypeCode)
                .userSeq(userSeq)
                .name(name)
                .backNumber(backNumber)
                .positionCode(positionCode)
                .email(email)
                .build();
    }
}