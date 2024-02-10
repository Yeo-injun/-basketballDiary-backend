package com.threeNerds.basketballDiary.mvc.game.domain;

import com.threeNerds.basketballDiary.constant.code.type.GameRecordAuthCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameRecordAuth {

    private Long gameRecordAuthSeq;     /* 게임기록권한Seq */
    private Long gameSeq;               /* 게임Seq */
    private Long teamMemberSeq;        /* 팀원Seq */
    private String gameRecordAuthCode;  /* 게임기록권한코드 */
    private String regDate;             /* 등록일자 */

    public static GameRecordAuth createCreator(Long gameSeq, Long teamMemberSeq) {
        return GameRecordAuth.builder()
                .gameSeq(gameSeq)
                .teamMemberSeq(teamMemberSeq)
                .gameRecordAuthCode(GameRecordAuthCode.CREATOR.getCode())
                .build();
    }

    public static GameRecordAuth createOnlyWriter(Long gameSeq, Long teamMemberSeq) {
        return GameRecordAuth.builder()
                .gameSeq(gameSeq)
                .teamMemberSeq(teamMemberSeq)
                .gameRecordAuthCode(GameRecordAuthCode.ONLY_WRITER.getCode())
                .build();
    }
}
