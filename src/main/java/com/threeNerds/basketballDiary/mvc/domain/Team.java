package com.threeNerds.basketballDiary.mvc.domain;

import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Team {
    // 팀SEQ
    private Long teamSeq;

    // 팀장ID - userSeq를 할당해주기? 확인필요
    private String leaderId;

    // 팀명
    private String teamName;

    // 연고지
    private String hometown;

    // 소개글
    private String introduction;

    // 창단일자
    private LocalDate foundationYmd;

    // 등록일자
    private LocalDate regDate;

    // 수정일자
    private LocalDate updateDate;

    // 시도코드
    private String sidoCode;

    // 시군구코드
    private String sigunguCode;
}
