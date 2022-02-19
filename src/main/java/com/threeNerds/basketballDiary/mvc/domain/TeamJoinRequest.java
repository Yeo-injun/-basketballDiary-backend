package com.threeNerds.basketballDiary.mvc.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Description;

import java.util.Date;

@Getter
@Builder
@AllArgsConstructor // 모든 필드를 파라미터로 받아서 생성자를 만들어 줌. 이를 통해 @Builder사용시 MyBatis에서 select 결과를 객체에 매핑 시켜줄 때 모든 필드를 가진 객체를 생성하여 오류 해결.
@NoArgsConstructor
public class TeamJoinRequest {
    // 팀가입요청Seq
    private Long teamJoinRequestSeq;

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
