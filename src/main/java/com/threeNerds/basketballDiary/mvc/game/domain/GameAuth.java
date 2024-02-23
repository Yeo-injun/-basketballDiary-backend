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
public class GameAuth {

    private Long gameRecordAuthSeq;     /* 게임기록권한Seq */
    private Long gameSeq;               /* 게임Seq */
    private Long userSeq;               /* 사용자Seq - 경기기록 권한은 사용자Seq를 기준으로 변경 2024.02.24 */
    private String gameRecordAuthCode;  /* 게임기록권한코드 */
    private String regDate;             /* 등록일자 */

    public static GameAuth createCreatorAuth( Long gameSeq, Long userSeq ) {
        return GameAuth.builder()
                .gameSeq( gameSeq )
                .userSeq( userSeq )
                .gameRecordAuthCode( GameRecordAuthCode.CREATOR.getCode() )
                .build();
    }

    public static GameAuth createRecordAuth( Long gameSeq, Long userSeq ) {
        return GameAuth.builder()
                .gameSeq( gameSeq )
                .userSeq( userSeq )
                .gameRecordAuthCode( GameRecordAuthCode.RECORDER.getCode() )
                .build();
    }
}
