package com.threeNerds.basketballDiary.mvc.myTeam.domain;

import com.threeNerds.basketballDiary.constant.code.type.JoinRequestStateCode;
import com.threeNerds.basketballDiary.constant.code.type.JoinRequestTypeCode;
import com.threeNerds.basketballDiary.mvc.authUser.service.dto.TeamInvitationCommand;
import com.threeNerds.basketballDiary.mvc.authUser.service.dto.JoinRequestCommand;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.CmnMyTeamDTO;
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
    public static TeamJoinRequest createJoinRequest( JoinRequestCommand command ) {
        return TeamJoinRequest.builder()
                .userSeq(               command.getUserSeq() )
                .teamSeq(               command.getTeamSeq() )
                .joinRequestTypeCode(   JoinRequestTypeCode.JOIN_REQUEST.getCode() )
                .joinRequestStateCode(  JoinRequestStateCode.WAITING.getCode() )
                .build();
    }

    /** 가입요청(사용자 -> 팀) 취소 */
    public static TeamJoinRequest cancelJoinRequest( JoinRequestCommand command ) {
        return TeamJoinRequest.builder()
                .teamJoinRequestSeq(    command.getTeamJoinRequestSeq() )
                .userSeq(               command.getUserSeq() )
                .joinRequestStateCode(  JoinRequestStateCode.CANCEL.getCode() )
                .build();
    }

    /** 승인처리 - 팀이 사용자의 가입요청을 */
    public static TeamJoinRequest approveJoinRequest( CmnMyTeamDTO joinRequest ) {
        return TeamJoinRequest.builder()
                .teamJoinRequestSeq(joinRequest.getTeamJoinRequestSeq())
                .teamSeq(joinRequest.getTeamSeq())
                .joinRequestStateCode(JoinRequestStateCode.APPROVAL.getCode())
                .build();
    }

    /** 거절처리 - 팀이 사용자의 가입요청을 */
    public static TeamJoinRequest rejectJoinRequest(CmnMyTeamDTO joinRequest) {
        return TeamJoinRequest.builder()
                .teamJoinRequestSeq(joinRequest.getTeamJoinRequestSeq())
                .teamSeq(joinRequest.getTeamSeq())
                .joinRequestStateCode(JoinRequestStateCode.REJECTION.getCode())
                .build();
    }

    /** 초대 생성(팀 -> 사용자) */
    public static TeamJoinRequest createInvitation( Long teamSeq, Long userSeq ) {
        return TeamJoinRequest.builder()
                .teamSeq(               teamSeq )
                .userSeq(               userSeq )
                .joinRequestTypeCode(   JoinRequestTypeCode.INVITATION.getCode() )
                .joinRequestStateCode(  JoinRequestStateCode.WAITING.getCode() )
                .build();
    }

    /** 초대 승낙처리 - 사용자가 팀의 초대를 */
    public static TeamJoinRequest approveInvitation( TeamInvitationCommand command ) {
        return TeamJoinRequest.builder()
                .teamJoinRequestSeq(    command.getTeamJoinRequestSeq() )
                .userSeq(               command.getUserSeq() )
                .joinRequestStateCode(  JoinRequestStateCode.APPROVAL.getCode() )
                .build();
    }

    /** 거절처리 - 팀이 사용자의 가입요청을 */
    public static TeamJoinRequest rejectInvitation( TeamInvitationCommand command ) {
        return TeamJoinRequest.builder()
                .teamJoinRequestSeq(    command.getTeamJoinRequestSeq() )
                .userSeq(               command.getUserSeq() )
                .joinRequestStateCode(  JoinRequestStateCode.REJECTION.getCode() )
                .build();
    }

}
