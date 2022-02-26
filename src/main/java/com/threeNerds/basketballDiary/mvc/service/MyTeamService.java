package com.threeNerds.basketballDiary.mvc.service;

import com.threeNerds.basketballDiary.mvc.domain.Team;
import com.threeNerds.basketballDiary.mvc.domain.TeamRegularExercise;
import com.threeNerds.basketballDiary.mvc.dto.ManagerDTO;
import com.threeNerds.basketballDiary.mvc.dto.MyTeamDTO;
import com.threeNerds.basketballDiary.mvc.dto.MyTeamInfoDTO;
import com.threeNerds.basketballDiary.mvc.dto.PlayerSearchDTO;
import com.threeNerds.basketballDiary.mvc.repository.MyTeamRepository;
import com.threeNerds.basketballDiary.mvc.repository.TeamRegularExerciseRepository;
import com.threeNerds.basketballDiary.mvc.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    private final MyTeamRepository myTeamRepository;
    private final TeamRepository teamRepository;
    private final TeamRegularExerciseRepository teamRegularExerciseRepository;

    public List<ManagerDTO> findManagers(Long teamSeq) {
        if(teamSeq == null)
            throw new NullPointerException("팀 PK가 존재하지 않습니다.");

        return myTeamRepository.findManagerByTeamSeq(teamSeq);
    }

    public List<MyTeamDTO> findTeams(Long userSeq) {
        if (userSeq == null)
            throw new NullPointerException("userSeq");

        List<MyTeamDTO> resultDTO = new ArrayList<MyTeamDTO>();
        List<MyTeamInfoDTO> myTeamInfoList = myTeamRepository.findAllByUserSeq(userSeq);

        myTeamInfoList.forEach(myTeamInfo -> {
            Long teamSeq = myTeamInfo.getTeamSeq();
            MyTeamDTO myTeamDTO = new MyTeamDTO()
                    .myTeamInfo(myTeamInfo)
                    .teamRegularExercisesList(teamRegularExerciseRepository.findByTeamSeq(teamSeq));
            resultDTO.add(myTeamDTO);
        });
        return resultDTO;
    }

    public MyTeamDTO findTeam(Long userSeq, Long teamSeq) {
        if (userSeq == null)
            throw new NullPointerException("userSeq");
        if (teamSeq == null)
            throw new NullPointerException("teamSeq");

        MyTeamInfoDTO myTeamInfo = myTeamRepository.findByUserSeqAndTeamSeq(userSeq, teamSeq);
        List<TeamRegularExercise> teamRegularExerciseList = teamRegularExerciseRepository.findByTeamSeq(teamSeq);

        if (myTeamInfo == null)
            throw new NullPointerException("소속팀의 해당 팀 정보가 존재하지 않습니다.");

        MyTeamDTO resultDTO = new MyTeamDTO()
                .myTeamInfo(myTeamInfo)
                .teamRegularExercisesList(teamRegularExerciseList);

        log.info("teamName = {}", myTeamInfo.getTeamName());
        return resultDTO;
    }

    public void modifyMyTeam(Long teamSeq, MyTeamDTO dto) {
        if(teamSeq == null)
            throw new NullPointerException("팀 PK가 존재하지 않습니다.");
        if(dto == null)
            throw new NullPointerException("소속팀 정보가 존재하지 않습니다.");

        // 1. @RequestBody 값 조회
        MyTeamInfoDTO paramMyTeamInfo = dto.getMyTeamInfo();
        List<TeamRegularExercise> paramTeamRegularExercise = dto.getTeamRegularExercisesList();

        // 2. 팀정보 수정
        Team team = teamRepository.findByTeamSeq(teamSeq);
        BeanUtils.copyProperties(paramMyTeamInfo, team);
        teamRepository.updateTeam(team);

        // 3. 정기운동 등록 및 수정
        paramTeamRegularExercise.forEach(param -> {
            Long teamRegularExerciseSeq = param.getTeamRegularExerciseSeq();
            // 3.1 팀 정기운동 Seq에 따라 등록 및 수정 분기
            if(teamRegularExerciseSeq != null) {
                // Seq가 있으므로 조회 후 수정내역 update
                TeamRegularExercise teamRegularExercise = teamRegularExerciseRepository.findByTeamRegularExerciseSeq(teamRegularExerciseSeq);
                BeanUtils.copyProperties(param, teamRegularExercise);
                teamRegularExerciseRepository.updateTeamRegularExercise(teamRegularExercise);
            } else {
                // Seq가 없으므로 신규내역 insert
                TeamRegularExercise teamRegularExercise = TeamRegularExercise.builder()
                        .teamSeq(teamSeq)
                        .dayOfTheWeekCode(param.getDayOfTheWeekCode())
                        .startTime(param.getStartTime())
                        .endTime(param.getEndTime())
                        .exercisePlaceAddress(param.getExercisePlaceAddress())
                        .exercisePlaceName(param.getExercisePlaceName())
                        .build();

                teamRegularExerciseRepository.saveTeamRegularExercise(teamRegularExercise);
            }
        });
    }

    public void deleteMyTeam(Long teamSeq) {
        if (teamSeq == null)
            throw new NullPointerException("teamSeq");

        teamRepository.deleteById(teamSeq);
    }
}