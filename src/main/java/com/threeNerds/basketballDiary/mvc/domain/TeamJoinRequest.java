package com.threeNerds.basketballDiary.mvc.domain;

import com.threeNerds.basketballDiary.constant.JoinRequestStateCode;
import com.threeNerds.basketballDiary.mvc.dto.JoinRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;

import java.time.LocalDate;
import java.util.*;

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
    private LocalDate requestDate;

    // 요청확정일시
    private LocalDate confirmationDate;

    /** 가입요청 승인처리 */
    public static TeamJoinRequest approve (JoinRequestDTO joinRequest)
    {
        return TeamJoinRequest.builder()
                .teamJoinRequestSeq(joinRequest.getTeamJoinRequestSeq())
                .teamSeq(joinRequest.getTeamSeq())
                .joinRequestStateCode(JoinRequestStateCode.APPROVAL.getCode())
                .build();
    }
 }
