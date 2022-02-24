package com.threeNerds.basketballDiary.mvc.domain;

import lombok.*;

import java.util.Date;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamMember {
    /* PK */
    private Long teamMemberSeq;
    /* FK : 유저Seq */
    private Long userSeq;
    /* FK : 팀Seq */
    private Long teamSeq;
    /* 멤버이미지경로 */
    private String memberImagePath;
    /* 가입일자 */
    private String joinYmd;
    /* 팀권한코드 */
    private String teamAuthCode;
    /* 등번호 */
    private String backNumber;
    /* 탈퇴여부 */
    private String withdrawalYn;
}
