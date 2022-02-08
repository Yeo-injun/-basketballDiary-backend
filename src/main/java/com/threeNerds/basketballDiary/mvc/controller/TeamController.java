package com.threeNerds.basketballDiary.mvc.controller;

import com.threeNerds.basketballDiary.mvc.domain.Team;
import com.threeNerds.basketballDiary.mvc.dto.TeamDto;
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
    public String create(TeamDto teamDto){
        Team team = new Team();

        team.setLeaderId(teamDto.getLeaderId());
        team.setTeamName(teamDto.getTeamName());
        team.setHometown(teamDto.getHometown());
        team.setIntroduction(teamDto.getIntroduction());

        LocalDate today = LocalDate.now();
        team.setFoundationYmd(today);
        team.setRegDate(today);
        team.setUpdateDate(today);

        team.setSidoCode(teamDto.getSidoCode());
        team.setSigunguCode(teamDto.getSigunguCode());

        teamService.createTeam(team);
        return "ok";
    }
}
