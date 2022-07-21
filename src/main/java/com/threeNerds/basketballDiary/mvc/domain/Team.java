package com.threeNerds.basketballDiary.mvc.domain;

import com.threeNerds.basketballDiary.mvc.dto.team.team.TeamDTO;
import lombok.*;

import java.time.LocalDate;
import java.time.ZoneId;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Team {
    // 팀SEQ
    private Long teamSeq;

    // 팀장ID
    private Long leaderId;

    // 팀명
    private String teamName;

    // 연고지
    private String hometown;

    // 소개글
    private String introduction;

    // 창단일자
    private String foundationYmd;

    // 등록일자
    private LocalDate regDate;

    // 수정일자
    private LocalDate updateDate;

    // 시도코드
    private String sidoCode;

    // 시군구코드
    private String sigunguCode;

    // 팀 이미지 경로
    private String teamImagePath;

    public static Team create(TeamDTO teamDTO)
    {
        LocalDate now = LocalDate.now(ZoneId.of("Asia/Seoul"));
        return Team.builder()
                    .teamName(teamDTO.getTeamName())
                    .hometown(teamDTO.getHometown())
                    .foundationYmd(teamDTO.getFoundationYmd())
                    .introduction(teamDTO.getIntroduction())
                    .teamImagePath(teamDTO.getTeamImagePath())
                    .leaderId(teamDTO.getLeaderId())
                    .regDate(now)
                    .updateDate(now)
                    .build();
    }
}
