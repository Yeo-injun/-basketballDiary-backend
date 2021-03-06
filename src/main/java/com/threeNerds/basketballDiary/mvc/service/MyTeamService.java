package com.threeNerds.basketballDiary.mvc.service;

import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.Error;
import com.threeNerds.basketballDiary.mvc.domain.Team;
import com.threeNerds.basketballDiary.mvc.domain.TeamRegularExercise;
import com.threeNerds.basketballDiary.mvc.dto.*;
import com.threeNerds.basketballDiary.mvc.dto.myTeam.myTeam.MemberDTO;
import com.threeNerds.basketballDiary.mvc.dto.myTeam.myTeam.MyTeamDTO;
import com.threeNerds.basketballDiary.mvc.repository.MyTeamRepository;
import com.threeNerds.basketballDiary.mvc.repository.TeamRegularExerciseRepository;
import com.threeNerds.basketballDiary.mvc.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
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
        // 소속팀 운영진 정보는 반드시 1건 이상(최소한 팀장이 존재해야함)이어야 하므로,
        // 조회내역이 존재하지 않으면 200 처리후 메시지를 전달한다.
        List<MemberDTO> resultManagerList
                = myTeamRepository.findAllManagerByTeamSeq(teamSeq);

        return resultManagerList.isEmpty() ?
                Collections.emptyList() : resultManagerList;
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
                .pagerDTO(pagerDTO);

        // 소속팀은 팀장과 운영진을 제외하므로, 팀원 정보가 존재하지 않더라도 404 처리하지 않는다.
        List<MemberDTO> resultMemberList = myTeamRepository.findPagingMemberByTeamSeq(memberDTO);

        return resultMemberList.isEmpty() ?
                Collections.emptyList() : resultMemberList;
    }

    /**
     * 소속팀 목록 조회
     * @param userSeq
     * @return List<MyTeamDTO>
     */
    public List<MyTeamDTO> findTeams(Long userSeq) {
        List<MyTeamDTO> resultMyTeamList = new ArrayList<MyTeamDTO>();
        List<MyTeamDTO> myTeamInfoList = myTeamRepository.findAllByUserSeq(userSeq);

        myTeamInfoList.forEach(myTeamInfo -> {
            Long teamSeq = myTeamInfo.getTeamSeq();
            List<TeamRegularExercise> exerciseList = teamRegularExerciseRepository.findByTeamSeq(teamSeq);
            MyTeamDTO myTeamDTO = new MyTeamDTO()
                    .teamSeq(myTeamInfo.getTeamSeq())
                    .teamName(myTeamInfo.getTeamName())
                    .teamImagePath(myTeamInfo.getTeamImagePath())
                    .hometown(myTeamInfo.getHometown())
                    .sidoCode(myTeamInfo.getSidoCode())
                    .sigunguCode(myTeamInfo.getSigunguCode())
                    .foundationYmd(myTeamInfo.getFoundationYmd())
                    .introduction(myTeamInfo.getIntroduction())
                    .totMember(myTeamInfo.getTotMember())
                    .teamRegularExercisesList(exerciseList.isEmpty() ? Collections.emptyList() : exerciseList);
            resultMyTeamList.add(myTeamDTO);
        });

        return resultMyTeamList.isEmpty() ?
                Collections.emptyList() : resultMyTeamList;
    }

    /**
     * 소속팀 단건 조회
     * @param userSeq, teamSeq
     * @return MyTeamDTO
     */
    public MyTeamDTO findTeam(Long userSeq, Long teamSeq) {
        // 소속되지 않은 팀에 대한 조회는 Interceptor에 의해 처리됨.

        MyTeamDTO myTeam = myTeamRepository.findByUserSeqAndTeamSeq(userSeq, teamSeq);
        List<TeamRegularExercise> teamRegularExerciseList
                = teamRegularExerciseRepository.findByTeamSeq(teamSeq);

        MyTeamDTO resultDTO = new MyTeamDTO()
                .teamSeq(myTeam.getTeamSeq())
                .teamName(myTeam.getTeamName())
                .teamImagePath(myTeam.getTeamImagePath())
                .hometown(myTeam.getHometown())
                .sidoCode(myTeam.getSidoCode())
                .sigunguCode(myTeam.getSigunguCode())
                .foundationYmd(myTeam.getFoundationYmd())
                .introduction(myTeam.getIntroduction())
                .totMember(myTeam.getTotMember())
                .teamRegularExercisesList(teamRegularExerciseList.isEmpty() ? Collections.emptyList() : teamRegularExerciseList);

        log.info("teamName = {}", myTeam.getTeamName());
        return resultDTO;
    }

    /**
     * 소속팀 수정
     * @param teamSeq, dto
     */
    public void modifyMyTeam(Long teamSeq, MyTeamDTO dto) {
        // 1. @RequestBody 값 조회
        List<TeamRegularExercise> paramTeamRegularExercise = dto.getTeamRegularExercisesList();

        // 2. 팀정보 수정
        // 2-1. 조회하려는 팀이 존재하지 않으므로 예외처리
        Team team = Optional.ofNullable(teamRepository.findByTeamSeq(teamSeq))
                .orElseThrow(() -> new CustomException(Error.MY_TEAM_NOT_FOUND));

        Team resultTeam = Team.builder()
                .teamSeq(teamSeq)
                .leaderId(team.getLeaderId())
                .teamName(dto.getTeamName())
                .teamImagePath(dto.getTeamImagePath())
                .hometown(dto.getHometown())
                .introduction(dto.getIntroduction())
                .foundationYmd(dto.getFoundationYmd())
                .regDate(team.getRegDate())
                .updateDate(LocalDate.now(ZoneId.of("Asia/Seoul")))
                .sidoCode(dto.getSidoCode())
                .sigunguCode(dto.getSigunguCode())
                .build();

        teamRepository.updateTeam(resultTeam);

        // 3. 정기운동 등록 및 수정
        paramTeamRegularExercise.forEach(param -> {
            Long teamRegularExerciseSeq = param.getTeamRegularExerciseSeq();
            // 3.1 팀 정기운동 Seq에 따라 등록 및 수정 분기
            if(teamRegularExerciseSeq != null) {
                // Seq가 있으므로 조회 후 수정내역 update
                TeamRegularExercise teamRegularExercise = Optional.ofNullable(teamRegularExerciseRepository.findByTeamRegularExerciseSeq(teamRegularExerciseSeq))
                                .orElseThrow(() -> new CustomException(Error.REGULAR_EXERCISE_NOT_FOUND));

                teamRegularExerciseRepository.updateTeamRegularExercise(TeamRegularExercise.builder()
                                .teamRegularExerciseSeq(teamRegularExerciseSeq)
                                .teamSeq(teamSeq)
                                .dayOfTheWeekCode(param.getDayOfTheWeekCode())
                                .startTime(param.getStartTime())
                                .endTime(param.getEndTime())
                                .exercisePlaceAddress(param.getExercisePlaceAddress())
                                .exercisePlaceName(param.getExercisePlaceName())
                                .build());
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
     * 소속팀 삭제(팀 삭제와 동일)
     * @param teamSeq
     */
    public void deleteMyTeam(Long teamSeq) {

        /**
         * 1. /:teamSeq 에 가 존재하는 메서드인가? (405)
         * 2. teamSeq가 유효한 형식인가? (400)
         * 3. teamSeq에 해당하는 정보가 존재하는가? (404)
         * 4. 헤더의 인증이 정확한가? (401)
         * 5. 삭제 권한이 있는가? (403)
         **/

        // 1. 소속팀이 존재하는지 체크
        Optional.ofNullable(teamRepository.findByTeamSeq(teamSeq))
                .orElseThrow(() -> new CustomException(Error.MY_TEAM_NOT_FOUND));

        teamRepository.deleteById(teamSeq);
    }
}