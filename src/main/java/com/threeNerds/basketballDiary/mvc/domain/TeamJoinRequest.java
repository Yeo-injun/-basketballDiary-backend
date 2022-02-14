package com.threeNerds.basketballDiary.mvc.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.context.annotation.Description;

import java.util.Date;

@Getter
@Builder
public class TeamJoinRequest {
    // 팀가입요청Seq
    private Long teamJoinSeq;

    // 팀Seq
    private Long teamSeq;

    // 사용자Seq
    private Long userSeq;

    // 가입요청 유형코드
    private String joinRequestTypeCode;
    // 가입요청상태 코드
    private String joinRequestStateCode;

    // 가입요청일시
    private Date requestDate; // TODO sql패키지와 util패키지의 Date차이는??
    // 요청확정일시
    private Date confirmationDate;



}
