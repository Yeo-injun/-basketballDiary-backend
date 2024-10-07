package com.threeNerds.basketballDiary.mvc.team.domain;

import com.threeNerds.basketballDiary.constant.code.type.TeamAuthCode;
import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.error.DomainErrorType;
import lombok.*;

import java.time.LocalDate;

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

    public TeamMember toManager() {
        if (!TeamAuthCode.TEAM_MEMBER.getCode().equals(this.teamAuthCode)) {
            throw new CustomException(DomainErrorType.CANT_APPOINTMENT_MANAGER);
        }
        this.teamAuthCode = TeamAuthCode.MANAGER.getCode();
        return this;
    }

    public TeamMember toMember() {
        if (!TeamAuthCode.MANAGER.getCode().equals(this.teamAuthCode)) {
            throw new CustomException(DomainErrorType.CANT_DISMISSAL_MANAGER);
        }
        this.teamAuthCode = TeamAuthCode.TEAM_MEMBER.getCode();
        return this;
    }

    public TeamMember toWithdrawal() {
        this.withdrawalYn = "Y";
        return this;
    }

    /**
     * 가입요청에 따른 팀원 객체 생성
     */
    public static TeamMember of( TeamJoinRequest joinRequest ) {
        return TeamMember.create( joinRequest.getTeamSeq(), joinRequest.getUserSeq(), TeamAuthCode.TEAM_MEMBER.getCode() );
    }

    public static TeamMember createLeader( Team team ) {
        return TeamMember.create( team.getTeamSeq(), team.getLeaderUserSeq(), TeamAuthCode.LEADER.getCode());
    }

    private static TeamMember create( Long teamSeq, Long userSeq, String teamAuthCode ) {
        String currentYmd = LocalDate.now().toString().replace("-", "");
        return TeamMember.builder()
                .teamSeq(teamSeq)
                .userSeq(userSeq)
                .teamAuthCode(teamAuthCode)
                .joinYmd(currentYmd)
                .withdrawalYn("N")
                .build();
    }



    /** 팀원객체가 파라미터로 던진 팀에 소속되어 있는지 확인 */
    public boolean isJoinTeam( Long teamSeq ) {
        // 팀원 객체가 소속팀 정보를 가지고 있는지 체크
        if ( null == this.teamSeq || null == teamSeq ) {
            return false;
        }
        return teamSeq.equals( this.teamSeq );
    }

    public boolean checkManagerAuth() {
        return TeamAuthCode.MANAGER.getCode().equals( this.teamAuthCode );
    }

    public boolean checkTeamMemberAuth() {
        return TeamAuthCode.TEAM_MEMBER.getCode().equals( this.teamAuthCode );
    }
}
