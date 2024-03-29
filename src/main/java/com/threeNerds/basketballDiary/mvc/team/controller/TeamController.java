package com.threeNerds.basketballDiary.mvc.team.controller;

import com.threeNerds.basketballDiary.auth.Auth;

import com.threeNerds.basketballDiary.mvc.myTeam.service.MyTeamAuthService;
import com.threeNerds.basketballDiary.mvc.myTeam.service.dto.TeamAuthDTO;
import com.threeNerds.basketballDiary.mvc.team.controller.request.RegisterTeamRequest;
import com.threeNerds.basketballDiary.mvc.team.controller.response.SearchTeamsResponse;
import com.threeNerds.basketballDiary.mvc.team.dto.SearchTeamDTO;
import com.threeNerds.basketballDiary.mvc.team.service.TeamService;
import com.threeNerds.basketballDiary.session.SessionUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import static com.threeNerds.basketballDiary.session.util.SessionUtil.LOGIN_USER;

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
    private final MyTeamAuthService myTeamAuthService;

    /**
     * API019 : 팀 목록 조회
     */
    @GetMapping
    public ResponseEntity<SearchTeamsResponse> searchTeams(
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

        return ResponseEntity.ok().body( teamService.searchTeams( searchTeamDTO ) );
    }

    /**
     * API021 : 팀 생성
     */
    @Auth
    @PostMapping
    public ResponseEntity<Void> createTeam(
            @SessionAttribute(value = LOGIN_USER, required = false) SessionUser sessionUser,
            @RequestPart( required = false ) MultipartFile teamLogoImage,
            @RequestPart @Valid RegisterTeamRequest teamInfo
    ) {
        Long loginUserSeq = sessionUser.getUserSeq();
        teamService.createTeam( new RegisterTeamRequest(
                loginUserSeq,
                teamInfo,
                teamLogoImage
        ) );

        /** 소속팀 권한정보 update */
        TeamAuthDTO authTeamInfo = myTeamAuthService.getAllTeamAuthInfo( TeamAuthDTO.of( loginUserSeq ) );
        sessionUser.setAuthTeams( authTeamInfo.getAuthTeams() );
        return ResponseEntity.ok().build();
    }
}
