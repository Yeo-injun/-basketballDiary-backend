package com.threeNerds.basketballDiary.mvc.team.domain;

import com.threeNerds.basketballDiary.constant.code.type.JoinRequestStateCode;
import com.threeNerds.basketballDiary.constant.code.type.JoinRequestTypeCode;
import com.threeNerds.basketballDiary.mvc.user.service.dto.TeamInvitationCommand;
import com.threeNerds.basketballDiary.mvc.user.service.dto.JoinRequestCommand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Objects;

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

    /** 승인상태로 변경 */
    public TeamJoinRequest toApproval() {
        return TeamJoinRequest.builder()
                .teamJoinRequestSeq(    this.teamJoinRequestSeq )
                .teamSeq(               this.teamSeq )
                .userSeq(               this.userSeq )
                .joinRequestStateCode(  JoinRequestStateCode.APPROVAL.getCode() )
                .build();
    }

    public TeamJoinRequest toRejection() {
        return TeamJoinRequest.builder()
                .teamJoinRequestSeq(    this.teamJoinRequestSeq )
                .teamSeq(               this.teamSeq )
                .userSeq(               this.userSeq )
                .joinRequestStateCode(  JoinRequestStateCode.REJECTION.getCode() )
                .build();
    }

    /** 가입요청의 수용여부를 결정할 수 있는 상태인지 검증하는 메소드 */
    public boolean checkDecisionEnabled( Long teamSeq ) {
        boolean isValidTeam = Objects.equals( this.teamSeq, teamSeq );
        boolean isPendingState = JoinRequestStateCode.WAITING.getCode().equals( this.joinRequestStateCode );
        return isValidTeam && isPendingState;
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
    @Deprecated
    public static TeamJoinRequest approveInvitation( TeamInvitationCommand command ) {
        return TeamJoinRequest.builder()
                .teamJoinRequestSeq(    command.getTeamJoinRequestSeq() )
                .userSeq(               command.getUserSeq() )
                .joinRequestStateCode(  JoinRequestStateCode.APPROVAL.getCode() )
                .build();
    }

    /** 거절처리 - 팀이 사용자의 가입요청을 */
    @Deprecated
    public static TeamJoinRequest rejectInvitation( TeamInvitationCommand command ) {
        return TeamJoinRequest.builder()
                .teamJoinRequestSeq(    command.getTeamJoinRequestSeq() )
                .userSeq(               command.getUserSeq() )
                .joinRequestStateCode(  JoinRequestStateCode.REJECTION.getCode() )
                .build();
    }

}
