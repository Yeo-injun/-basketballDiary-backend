package com.threeNerds.basketballDiary.mvc.controller;

import com.threeNerds.basketballDiary.interceptor.Auth;
import com.threeNerds.basketballDiary.mvc.dto.PagerDTO;
import com.threeNerds.basketballDiary.mvc.dto.team.team.SearchTeamDTO;
import com.threeNerds.basketballDiary.mvc.dto.team.team.TeamDTO;
import com.threeNerds.basketballDiary.mvc.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * ... 수행하는 Controller
 * @author 책임자 작성
 *
 * issue and history
 * <pre>
 * 2022.02.08 여인준 : 소스코드 생성
 * 2022.03.14 강창기 : 팀 조회 구현
 * </pre>
 */

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamService teamService;

    /**ㅎ
     * API019 : 팀 목록 조회
     */
    @GetMapping
    public ResponseEntity<List<TeamDTO>> searchTeams(
      @RequestParam(name = "team-name"  , required = false) String teamName,
      @RequestParam(name = "sigungu"    , required = false) String sigungu,
      @RequestParam(name = "start-day"  , required = false) String startDay,
      @RequestParam(name = "end-day"    , required = false) String endDay,
      @RequestParam(name = "start-time" , required = false) String startTime,
      @RequestParam(name = "end-time"   , required = false) String endTime,
      @RequestParam(name = "pageNo"     , defaultValue = "0") Integer pageNo
    ) {
        log.info("▒▒▒▒▒ API019: TeamController.searchTeams");
        PagerDTO pagerDTO = new PagerDTO()
                .pageNo(pageNo*5)
                .offset(5);
        SearchTeamDTO searchTeamDTO = new SearchTeamDTO()
                .teamName(teamName)
                .sigungu(sigungu)
                .startDay(startDay)
                .endDay(endDay)
                .startTime(startTime)
                .endTime(endTime)
                .pagerDTO(pagerDTO);

        List<TeamDTO> teamList = teamService.searchTeams(searchTeamDTO);
        teamList = teamList.isEmpty() ? Collections.emptyList() : teamList;

        return ResponseEntity.ok().body(teamList);
    }

    /**
     * API021 : 팀 등록
     */
    @Auth(GRADE = 0L)
    @PostMapping
    public ResponseEntity<?> registerTeam() {
        log.info("▒▒▒▒▒ API021: TeamController.registerTeam");

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
