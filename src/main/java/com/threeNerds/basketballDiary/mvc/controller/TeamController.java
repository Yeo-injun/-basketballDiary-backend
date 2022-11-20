package com.threeNerds.basketballDiary.mvc.controller;

import com.threeNerds.basketballDiary.interceptor.Auth;

import com.threeNerds.basketballDiary.mvc.dto.pagination.PaginatedTeamDTO;
import com.threeNerds.basketballDiary.mvc.dto.TeamAuthDTO;
import com.threeNerds.basketballDiary.mvc.dto.team.team.SearchTeamDTO;
import com.threeNerds.basketballDiary.mvc.dto.team.team.TeamDTO;
import com.threeNerds.basketballDiary.mvc.service.TeamService;
import com.threeNerds.basketballDiary.session.SessionUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.threeNerds.basketballDiary.constant.Constant.USER;
import static com.threeNerds.basketballDiary.constant.HttpResponseConst.RESPONSE_CREATED;
import static com.threeNerds.basketballDiary.utils.SessionUtil.LOGIN_USER;

/**
 * ... 수행하는 Controller
 * @author 책임자 작성
 *
 * issue and history
 * <pre>
 * 2022.02.08 여인준 : 소스코드 생성
 * 2022.03.14 강창기 : 팀 목록 조회 구현
 * 2022.03.23 강창기 : 팀 생성 구현
 * </pre>
 */

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamService teamService;

    /**
     * API019 : 팀 목록 조회
     */
    @GetMapping
    public ResponseEntity<?> searchTeams(
            @RequestParam(name = "team-name"  , required = false) String teamName,
            @RequestParam(name = "sigungu"    , required = false) String sigungu,
            @RequestParam(name = "start-day"  , required = false) String startDay,
            @RequestParam(name = "end-day"    , required = false) String endDay,
            @RequestParam(name = "start-time" , required = false) String startTime,
            @RequestParam(name = "end-time"   , required = false) String endTime,
            @RequestParam(name = "page-no"     , defaultValue = "0") Integer pageNo
    ) {
        log.info("▒▒▒▒▒ API019: TeamController.searchTeams");
        SearchTeamDTO searchTeamDTO = new SearchTeamDTO()
                .teamName(teamName)
                .sigungu(sigungu)
                .startDay(startDay)
                .endDay(endDay)
                .startTime(startTime)
                .endTime(endTime)
                .pageNo(pageNo);

        PaginatedTeamDTO teamList = teamService.searchTeams(searchTeamDTO);
        return ResponseEntity.ok().body(teamList);
    }

    /**
     * API021 : 팀 등록
     */
    @Auth(GRADE = USER)
    @PostMapping
    public ResponseEntity<?> registerTeam(
            @SessionAttribute(value = LOGIN_USER, required = false) SessionUser sessionUser,
            @RequestBody @Valid TeamDTO teamDTO
    ) {
        log.info("▒▒▒▒▒ API021: TeamController.registerTeam");

        Long userSeq = sessionUser.getUserSeq();
        teamDTO.leaderUserSeq(userSeq);
        List<TeamAuthDTO> authList = teamService.createTeam(teamDTO);
        sessionUser.updateAuthority(authList);

        return RESPONSE_CREATED;
    }
}
