package com.threeNerds.basketballDiary.mvc.service;

import com.threeNerds.basketballDiary.mvc.domain.Team;
import com.threeNerds.basketballDiary.mvc.domain.TeamRegularExercise;
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

    public List<MyTeamDTO> findTeams(Long userSeq) {
        if (userSeq == null)
            throw new NullPointerException("userSeq");

        List<MyTeamDTO> resultDTO = new ArrayList<MyTeamDTO>();
        List<MyTeamInfoDTO> myTeamInfoList = myTeamRepository.findAllByUserSeq(userSeq);

        myTeamInfoList.forEach(myTeamInfo -> {
            Long teamSeq = myTeamInfo.getTeamSeq();
            MyTeamDTO myTeamDTO = new MyTeamDTO();
            myTeamDTO.setMyTeamInfoDTO(myTeamInfo); // 소속팀정보
            myTeamDTO.setTeamRegularExercisesList(teamRegularExerciseRepository.findByTeamSeq(teamSeq)); // 정기운동목록
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

        MyTeamDTO resultDTO = new MyTeamDTO();
        resultDTO.setMyTeamInfoDTO(myTeamInfo);
        resultDTO.setTeamRegularExercisesList(teamRegularExerciseList);

        log.info("teamName = {}", myTeamInfo.getTeamName());
        return resultDTO;
    }

    public void modifyMyTeam(Long teamSeq, MyTeamDTO dto) {
        if(teamSeq == null)
            throw new NullPointerException("팀 PK가 존재하지 않습니다.");


    }

}