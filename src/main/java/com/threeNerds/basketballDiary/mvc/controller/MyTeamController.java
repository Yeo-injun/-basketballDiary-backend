package com.threeNerds.basketballDiary.mvc.controller;

import com.threeNerds.basketballDiary.mvc.dto.MyTeamDTO;
import com.threeNerds.basketballDiary.mvc.service.MyTeamService;
import com.threeNerds.basketballDiary.mvc.service.TeamMemberManagerService;
import com.threeNerds.basketballDiary.session.SessionDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.threeNerds.basketballDiary.session.SessionConst.LOGIN_MEMBER;

/**
 * ... 수행하는 Controller
 *
 * @author 책임자 작성
 * <p>
 * issue and history
 * <pre>
 * 2022.02.08 여인준 : 소스코드 생성
 * 2022.02.11 강창기 : 소속팀 목록조회 구현
 * </pre>
 */


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/myTeams")
public class MyTeamController {

    private final MyTeamService myTeamService;

    // API014: 소속팀 목록 조회
    @GetMapping
    public List<MyTeamDTO> searchMyTeams(@SessionAttribute(value = LOGIN_MEMBER, required = false) SessionDTO sessionDTO) {
        Long userSeq = sessionDTO.getUserSeq();

        List<MyTeamDTO> myTeamList = myTeamService.findMyTeams(userSeq);

        return myTeamList;
    }

    // API016: 소속팀 정보 수정데이터 단건 조회
    @GetMapping("/{teamSeq}/info")
    public MyTeamDTO searchMyTeam(@SessionAttribute(value = LOGIN_MEMBER, required = false) SessionDTO sessionDTO, @PathVariable(value = "teamSeq") Long teamSeq) {
        Long userSeq = sessionDTO.getUserSeq();

        MyTeamDTO myTeam = myTeamService.findMyTeam(userSeq, teamSeq);

        return myTeam;
    }

    // API017: 소속팀 정보 수정
    @PutMapping("/{teamSeq}/info")
    public void modifyMyTeam(@SessionAttribute(value = LOGIN_MEMBER, required = false) SessionDTO sessionDTO, @PathVariable(value = "teamSeq") Long teamSeq, @RequestBody MyTeamDTO dto) {
        Long userSeq = sessionDTO.getUserSeq();

        if(userSeq == null)
            throw new IllegalStateException("세션 정보가 존재하지 않습니다.");

        myTeamService.modifyMyTeam(teamSeq, dto);
    }
}
