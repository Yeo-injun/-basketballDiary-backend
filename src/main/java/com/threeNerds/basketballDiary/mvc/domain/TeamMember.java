package com.threeNerds.basketballDiary.mvc.domain;

import com.threeNerds.basketballDiary.constant.Constant;
import com.threeNerds.basketballDiary.constant.TeamAuthCode;
import com.threeNerds.basketballDiary.mvc.dto.loginUser.userTeamManager.JoinRequestDTO;
import com.threeNerds.basketballDiary.mvc.dto.myTeam.CmnMyTeamDTO;
import com.threeNerds.basketballDiary.mvc.dto.team.team.TeamDTO;
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

    // TODO 도메인객체를 추상화시켜서 부모클래스로 구현하기
    public boolean isExist() {
        if (this.teamMemberSeq == null) {
            return false;
        }
        return true;
    }
    /* TODO 도메인 객체의 기본적인 데이터 세팅 동작을 메소드로 구현 */
    public static TeamMember toManager(CmnMyTeamDTO teamMember)
    {
        return TeamMember.builder()
                .teamSeq(teamMember.getTeamSeq())
                .teamMemberSeq(teamMember.getTeamMemberSeq())
                .teamAuthCode(TeamAuthCode.MANAGER.getCode())
                .build();
    }

    public static TeamMember toMember(CmnMyTeamDTO teamMember)
    {
        return TeamMember.builder()
                .teamSeq(teamMember.getTeamSeq())
                .teamMemberSeq(teamMember.getTeamMemberSeq())
                .teamAuthCode(TeamAuthCode.TEAM_MEMBER.getCode())
                .build();
    }

    public static TeamMember create(TeamJoinRequest joinRequest) {
        return TeamMember.create(joinRequest.getTeamSeq(), joinRequest.getUserSeq(), TeamAuthCode.TEAM_MEMBER.getCode());
    }

    public static TeamMember create(Team team) {
        return TeamMember.create(team.getTeamSeq(), team.getLeaderId(), TeamAuthCode.TEAM_MEMBER.getCode());
    }

    public static TeamMember createLeader(Team newTeam) {
        return TeamMember.create(newTeam.getTeamSeq(), newTeam.getLeaderId(), TeamAuthCode.LEADER.getCode());
    }

    private static TeamMember create(Long teamSeq, Long userSeq, String teamAuthCode)
    {
        String currentYmd = LocalDate.now().toString().replace("-", "");
        return TeamMember.builder()
                .teamSeq(teamSeq)
                .userSeq(userSeq)
                .teamAuthCode(teamAuthCode)
                .joinYmd(currentYmd)
                .withdrawalYn(Constant.NO)
                .build();
    }

    public static TeamMember withdrawalMember(CmnMyTeamDTO teamMember)
    {
        return TeamMember.builder()
                .teamMemberSeq(teamMember.getTeamMemberSeq())
                .teamSeq(teamMember.getTeamSeq())
                .withdrawalYn(Constant.YES)
                .build();
    }


}
