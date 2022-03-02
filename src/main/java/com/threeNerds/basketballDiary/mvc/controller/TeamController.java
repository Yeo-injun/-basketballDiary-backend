package com.threeNerds.basketballDiary.mvc.controller;

import com.threeNerds.basketballDiary.mvc.domain.Team;
import com.threeNerds.basketballDiary.mvc.dto.TeamDTO;
import com.threeNerds.basketballDiary.mvc.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * ... 수행하는 Controller
 * @author 책임자 작성
 *
 * issue and history
 * <pre>
 * 2022.02.08 여인준 : 소스코드 생성
 * </pre>
 */

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamService teamService;

    @PostMapping("/new")
    public String create(TeamDTO teamDto){
        Team team = Team.builder()
                .leaderId(teamDto.getLeaderId())
                .teamName(teamDto.getTeamName())
                .hometown(teamDto.getHometown())
                .introduction(teamDto.getIntroduction())
                .foundationYmd(LocalDate.now())
                .regDate(LocalDate.now())
                .updateDate(LocalDate.now())
                .sidoCode(teamDto.getSidoCode())
                .sigunguCode(teamDto.getSigunguCode())
                .build();

        teamService.createTeam(team);
        return "ok";
    }
}
