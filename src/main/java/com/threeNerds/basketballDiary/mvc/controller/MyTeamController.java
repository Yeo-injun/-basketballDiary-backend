package com.threeNerds.basketballDiary.mvc.controller;

import com.threeNerds.basketballDiary.mvc.service.MyTeamService;
import com.threeNerds.basketballDiary.mvc.service.TeamMemberManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/api/myTemas")
public class MyTeamController {

    private final MyTeamService myTeamService;
    private final TeamMemberManagerService teamMemberManagerService;

    // API구현 예정
}
