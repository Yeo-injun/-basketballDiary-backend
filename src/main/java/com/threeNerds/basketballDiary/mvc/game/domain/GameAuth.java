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
    private Long gameJoinPlayerSeq;     /* 경기참가선수Seq - 경기에 참가한 선수여야 함 ( 다른팀에 속한 게스트도 경기기록권한 받을 수 있음 ) */
    private String gameRecordAuthCode;  /* 게임기록권한코드 */
    private String regDate;             /* 등록일자 */


    public static GameAuth ofCreator( Long gameSeq, Long userSeq, Long gameJoinPlayerSeq ) {
        return GameAuth.builder()
                .gameSeq(               gameSeq )
                .userSeq(               userSeq )
                .gameJoinPlayerSeq(     gameJoinPlayerSeq )
                .gameRecordAuthCode(    GameRecordAuthCode.CREATOR.getCode() )
                .build();
    }
    public static GameAuth ofRecorder( Long gameSeq, Long userSeq, Long gameJoinPlayerSeq ) {
        return GameAuth.builder()
                .gameSeq( gameSeq )
                .userSeq( userSeq )
                .gameJoinPlayerSeq(     gameJoinPlayerSeq )
                .gameRecordAuthCode( GameRecordAuthCode.RECORDER.getCode() )
                .build();
    }

}
