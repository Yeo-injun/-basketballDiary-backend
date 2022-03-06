package com.threeNerds.basketballDiary.mvc.service;

import com.threeNerds.basketballDiary.mvc.domain.Team;
import com.threeNerds.basketballDiary.mvc.domain.TeamRegularExercise;
import com.threeNerds.basketballDiary.mvc.dto.*;
import com.threeNerds.basketballDiary.mvc.repository.MyTeamRepository;
import com.threeNerds.basketballDiary.mvc.repository.TeamRegularExerciseRepository;
import com.threeNerds.basketballDiary.mvc.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * 소속팀에서 팀원관리 및 소속팀정보 관리 등의 업무를 수행하는 Service
 * @author 책임자 작성
 *
 * issue and history
 * <pre>
 * 2022.02.08 여인준 : 소스코드 생성
 * 2022.02.13 강창기 : 소속팀 목록조회 구현
 * 2022.02.23 강창기 : 소속팀 정보 수정 구현
 * 2022.02.24 강창기 : 소속팀 정보 삭제 구현
 * 2022.02.26 강창기 : 소속팀 운영진 조회 구현
 * 2022.02.27 강창기 : 소속팀 팀원 조회 구현
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

    /**
     * 소속팀 운영진 목록 조회
     * @param teamSeq
     * @return List<MemberDTO>
     */
    public List<MemberDTO> findManagers(Long teamSeq) {
        // 운영진은 반드시 1명 이상 존재해야 하므로(팀장), 아무도 존재하지 않을 경우 Exception 처리.
        return Optional.ofNullable(myTeamRepository.findAllManagerByTeamSeq(teamSeq))
                .orElseThrow(() -> new NullPointerException("운영진 정보가 존재하지 않습니다."));
    }

    /**
     * 소속팀 팀원 목록 조회
     * @param teamSeq, pageNo
     * @return List<MemberDTO>
     */
    public List<MemberDTO> findMembers(Long teamSeq, Integer pageNo) {
        PagerDTO pagerDTO = new PagerDTO()
                .pageNo(pageNo*4)
                .offset(4);
        MemberDTO memberDTO = new MemberDTO()
                .teamSeq(teamSeq)
                .pagerVO(pagerDTO);

        List<MemberDTO> memberList = myTeamRepository.findPagingMemberByTeamSeq(memberDTO);
        return memberList.isEmpty() ? Collections.emptyList() : memberList;
    }

    /**
     * 소속팀 목록 조회
     * @param userSeq
     * @return List<MyTeamDTO>
     */
    public List<MyTeamDTO> findTeams(Long userSeq) {
        List<MyTeamDTO> resultDTO = new ArrayList<MyTeamDTO>();
        List<MyTeamInfoDTO> myTeamInfoList = Optional.ofNullable(myTeamRepository.findAllByUserSeq(userSeq))
                .orElseThrow(() -> new NullPointerException("소속팀 정보가 존재하지 않습니다."));

        myTeamInfoList.forEach(myTeamInfo -> {
            Long teamSeq = myTeamInfo.getTeamSeq();
            List<TeamRegularExercise> exerciseList = teamRegularExerciseRepository.findByTeamSeq(teamSeq);
            MyTeamDTO myTeamDTO = new MyTeamDTO()
                    .myTeamInfo(myTeamInfo)
                    .teamRegularExercisesList(exerciseList.isEmpty() ? Collections.emptyList() : exerciseList);

            resultDTO.add(myTeamDTO);
        });
        return resultDTO;
    }

    /**
     * 소속팀 단건 조회
     * @param userSeq, teamSeq
     * @return MyTeamDTO
     */
    public MyTeamDTO findTeam(Long userSeq, Long teamSeq) {
        MyTeamInfoDTO myTeamInfo = Optional.ofNullable(myTeamRepository.findByUserSeqAndTeamSeq(userSeq, teamSeq))
                .orElseThrow(() -> new NullPointerException("소속팀의 해당 팀 정보가 존재하지 않습니다."));
        List<TeamRegularExercise> teamRegularExerciseList = teamRegularExerciseRepository.findByTeamSeq(teamSeq);

        MyTeamDTO resultDTO = new MyTeamDTO()
                .myTeamInfo(myTeamInfo)
                .teamRegularExercisesList(teamRegularExerciseList.isEmpty() ? Collections.emptyList() : teamRegularExerciseList);

        log.info("teamName = {}", myTeamInfo.getTeamName());
        return resultDTO;
    }

    /**
     * 소속팀 수정
     * @param teamSeq, dto
     */
    public void modifyMyTeam(Long teamSeq, MyTeamDTO dto) {
        // 1. @RequestBody 값 조회
        MyTeamInfoDTO paramMyTeamInfo = dto.getMyTeamInfo();
        List<TeamRegularExercise> paramTeamRegularExercise = dto.getTeamRegularExercisesList();

        // 2. 팀정보 수정
        Team team = Optional.ofNullable(teamRepository.findByTeamSeq(teamSeq))
                .orElseThrow(() -> new NullPointerException("팀 정보가 존재하지 않습니다."));
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

    /**
     * 소속팀 삭제
     * @param teamSeq
     */
    public void deleteMyTeam(Long teamSeq) {
        teamRepository.deleteById(teamSeq);
    }
}