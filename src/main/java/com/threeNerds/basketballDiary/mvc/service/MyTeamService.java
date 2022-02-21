package com.threeNerds.basketballDiary.mvc.service;

import com.threeNerds.basketballDiary.mvc.domain.Team;
import com.threeNerds.basketballDiary.mvc.domain.TeamRegularExercise;
import com.threeNerds.basketballDiary.mvc.dto.MyTeamDTO;
import com.threeNerds.basketballDiary.mvc.repository.MyTeamRepository;
import com.threeNerds.basketballDiary.mvc.repository.TeamRegularExerciseRepository;
import com.threeNerds.basketballDiary.mvc.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 소속팀에서 팀원관리 및 소속팀정보 관리 등의 업무를 수행하는 Service
 * @author 책임자 작성
 *
 * issue and history
 * <pre>
 * 2022.02.08 여인준 : 소스코드 생성
 * 2022.02.13 강창기 : 소속팀 목록조회 구현
 * </pre>
 */

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MyTeamService {

    private MyTeamRepository myTeamRepository;
    private TeamRepository teamRepository;
    private TeamRegularExerciseRepository teamRegularExerciseRepository;

    public List<MyTeamDTO> findMyTeams(Long userSeq) {
        if(userSeq == null)
            throw new NullPointerException("유저 PK가 존재하지 않습니다.");

        List<MyTeamDTO> myTeamList = myTeamRepository.findAllByUserSeq(userSeq);

        myTeamList.forEach(myTeamDTO -> log.info("teamName = {}, authCode = {}"
                , myTeamDTO.getTeamName(), myTeamDTO.getTeamAuthCode()));
        return myTeamList;
    }

    public MyTeamDTO findMyTeam(Long userSeq, Long teamSeq) {
        if(userSeq == null)
            throw new NullPointerException("유저 PK가 존재하지 않습니다.");
        if(teamSeq == null)
            throw new NullPointerException("팀 PK가 존재하지 않습니다.");

        MyTeamDTO myTeam = myTeamRepository.findByUserSeqAndTeamSeq(userSeq, teamSeq);

        if(myTeam == null)
            throw new NullPointerException("소속팀의 해당 팀 정보가 존재하지 않습니다.");

        log.info("teamName = {}", myTeam.getTeamName());
        return myTeam;
    }

    public void modifyMyTeam(Long teamSeq, MyTeamDTO dto) {
        if(teamSeq == null)
            throw new NullPointerException("팀 PK가 존재하지 않습니다.");

        // 1. 기존 소속팀 정보 조회
        Team team = teamRepository.findByTeamSeq(teamSeq);
        List<TeamRegularExercise> teamRegularExerciseList
                = teamRegularExerciseRepository.findByTeamSeq(teamSeq);

        // 2. 기존 소속팀에 묶인 팀 정보에 수정된 항목을 복사
        BeanUtils.copyProperties(dto, team);

        // 3. 소속팀에 묶인 팀 테이블에 update
        teamRepository.updateTeam(team);

    }
}
