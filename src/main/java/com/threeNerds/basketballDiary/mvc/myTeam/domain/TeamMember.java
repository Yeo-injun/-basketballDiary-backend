package com.threeNerds.basketballDiary.mvc.myTeam.domain;

import com.threeNerds.basketballDiary.constant.code.TeamAuthCode;
import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.mvc.team.domain.Team;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.CmnMyTeamDTO;
import com.threeNerds.basketballDiary.exception.Error;
import lombok.*;

import java.time.LocalDate;
import java.util.Optional;

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

    public TeamMember toManager()
    {
        if (!TeamAuthCode.TEAM_MEMBER.getCode().equals(this.teamAuthCode)) {
            throw new CustomException(Error.CANT_APPOINTMENT_MANAGER);
        }
        this.teamAuthCode = TeamAuthCode.MANAGER.getCode();
        return this;
    }

    public TeamMember toMember()
    {
        if (!TeamAuthCode.MANAGER.getCode().equals(this.teamAuthCode)) {
            throw new CustomException(Error.CANT_DISMISSAL_MANAGER);
        }
        this.teamAuthCode = TeamAuthCode.TEAM_MEMBER.getCode();
        return this;
    }

    public static TeamMember create(TeamJoinRequest joinRequest) {
        return TeamMember.create(joinRequest.getTeamSeq(), joinRequest.getUserSeq(), TeamAuthCode.TEAM_MEMBER.getCode());
    }

    public static TeamMember create(Team team) {
        return TeamMember.create(team.getTeamSeq(), team.getLeaderUserSeq(), TeamAuthCode.TEAM_MEMBER.getCode());
    }

    public static TeamMember createLeader(Team newTeam) {
        return TeamMember.create(newTeam.getTeamSeq(), newTeam.getLeaderUserSeq(), TeamAuthCode.LEADER.getCode());
    }

    private static TeamMember create(Long teamSeq, Long userSeq, String teamAuthCode)
    {
        String currentYmd = LocalDate.now().toString().replace("-", "");
        return TeamMember.builder()
                .teamSeq(teamSeq)
                .userSeq(userSeq)
                .teamAuthCode(teamAuthCode)
                .joinYmd(currentYmd)
                .withdrawalYn("N")
                .build();
    }

    public static TeamMember withdrawalMember(CmnMyTeamDTO teamMember)
    {
        return TeamMember.builder()
                .teamMemberSeq(teamMember.getTeamMemberSeq())
                .teamSeq(teamMember.getTeamSeq())
                .withdrawalYn("Y")
                .build();
    }


    /** 팀원객체가 파라미터로 던진 팀에 소속되어 있는지 확인 */
    public boolean isJoinTeam(Long teamSeq)
    {
        // 팀원 객체가 소속팀 정보를 가지고 있는지 체크
        if (Optional.ofNullable(this.teamSeq).isEmpty()) {
            return false;
        }
        
        // 팀원 객체가 소속팀 정보를 가지고 있다면 
        // 넘겨받은 팀seq의 null체크 - null일경우 -1값을 반영하여 팀seq가 무조건 일치하지 않도록 처리
        teamSeq = Optional.ofNullable(teamSeq)
                            .orElseGet(()-> Long.valueOf(-1));

        // 팀원 객체가 가지고 있는 팀seq와 넘겨받은 팀seq가 같을 경우에만 true반환
        if (teamSeq.equals(this.teamSeq)) {
            return true;
        }
        return false;
    }

    public boolean isNotJoinTeam(Long teamSeq) {
        return !isJoinTeam(teamSeq);
    }
}
