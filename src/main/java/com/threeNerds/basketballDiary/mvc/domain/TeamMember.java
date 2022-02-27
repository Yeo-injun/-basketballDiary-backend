package com.threeNerds.basketballDiary.mvc.domain;

import com.threeNerds.basketballDiary.constant.State;
import com.threeNerds.basketballDiary.constant.TeamAuthCode;
import com.threeNerds.basketballDiary.mvc.dto.KeyDTO;
import lombok.*;

import java.util.Date;

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

    /* TODO 도메인 객체의 기본적인 데이터 세팅 동작을 메소드로 구현 */
    public static TeamMember toManager(KeyDTO.TeamMember keys)
    {
        return TeamMember.builder()
                .teamSeq(keys.getTeamSeq())
                .teamMemberSeq(keys.getTeamMemberSeq())
                .teamAuthCode(TeamAuthCode.MANGER.getCode())
                .build();
    }

    public static TeamMember toMember(KeyDTO.TeamMember keys)
    {
        return TeamMember.builder()
                .teamSeq(keys.getTeamSeq())
                .teamMemberSeq(keys.getTeamMemberSeq())
                .teamAuthCode(TeamAuthCode.TEAM_MEMBER.getCode())
                .build();
    }

}
