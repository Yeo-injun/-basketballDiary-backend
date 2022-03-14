package com.threeNerds.basketballDiary.mvc.domain;

import com.threeNerds.basketballDiary.constant.JoinRequestStateCode;
import com.threeNerds.basketballDiary.constant.JoinRequestTypeCode;
import com.threeNerds.basketballDiary.mvc.dto.loginUser.CmnLoginUserDTO;
import com.threeNerds.basketballDiary.mvc.dto.loginUser.userTeamManager.JoinRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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

    /** 가입요청(사용자 -> 팀) 생성 */
    public static TeamJoinRequest createJoinRequest (CmnLoginUserDTO loginUserDTO)
    {
        return TeamJoinRequest.builder()
                .userSeq(loginUserDTO.getUserSeq())
                .teamSeq(loginUserDTO.getTeamSeq())
                .joinRequestTypeCode(JoinRequestTypeCode.JOIN_REQUEST.getCode())
                .joinRequestStateCode(JoinRequestStateCode.WAITING.getCode())
                .build();
    }

    /** 가입요청(사용자 -> 팀) 취소 */
    public static TeamJoinRequest cancelJoinRequest (CmnLoginUserDTO loginUserDTO)
    {
        return TeamJoinRequest.builder()
                .teamJoinRequestSeq(loginUserDTO.getTeamJoinRequestSeq())
                .userSeq(loginUserDTO.getUserSeq())
                .joinRequestStateCode(JoinRequestStateCode.CANCEL.getCode())
                .build();
    }

    /** 초대 생성(팀 -> 사용자) */
    public static TeamJoinRequest createInvitation (JoinRequestDTO joinRequest)
    {
        return TeamJoinRequest.builder()
                    .teamSeq(joinRequest.getTeamSeq())
                    .userSeq(joinRequest.getUserSeq())
                    .joinRequestTypeCode(JoinRequestTypeCode.INVITATION.getCode())
                    .joinRequestStateCode(JoinRequestStateCode.WAITING.getCode())
                    .build();
    }

    /** 승인처리 */
    public static TeamJoinRequest approve (JoinRequestDTO joinRequest)
    {
        return TeamJoinRequest.builder()
                .teamJoinRequestSeq(joinRequest.getTeamJoinRequestSeq())
                .teamSeq(joinRequest.getTeamSeq())
                .joinRequestStateCode(JoinRequestStateCode.APPROVAL.getCode())
                .build();
    }
 }
