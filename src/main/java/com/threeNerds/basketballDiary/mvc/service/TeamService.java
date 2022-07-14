package com.threeNerds.basketballDiary.mvc.service;

import com.threeNerds.basketballDiary.mvc.domain.Team;
import com.threeNerds.basketballDiary.mvc.domain.TeamRegularExercise;
import com.threeNerds.basketballDiary.mvc.dto.team.team.SearchTeamDTO;
import com.threeNerds.basketballDiary.mvc.dto.team.team.TeamDTO;
import com.threeNerds.basketballDiary.mvc.repository.TeamRegularExerciseRepository;
import com.threeNerds.basketballDiary.mvc.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 소속팀에서 팀원관리 및 소속팀정보 관리 등의 업무를 수행하는 Service
 * @author 책임자 작성
 *
 * issue and history
 * <pre>
 * 2022.02.08 여인준 : 소스코드 생성
 * 2022..14 강창기 : 팀 목록 조회 구현
 * 2022.03.23 강창기 : 팀 생성 구현
 * </pre>
 */

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamRegularExerciseRepository teamRegularExerciseRepository;

    /**
     * 팀 목록 조회
     * @return List<TeamDTO>
     */
    public List<TeamDTO> searchTeams(SearchTeamDTO searchTeamDTO) {
        log.info("TeamService.searchTeams");
        if (searchTeamDTO.getStartTime() != null
            && searchTeamDTO.getEndTime() != "")
        {
            searchTeamDTO
                    .startTime(searchTeamDTO.getStartTime().replace(":", ""))
                    .endTime(searchTeamDTO.getEndTime().replace(":", ""));
        }

        List<TeamDTO> resultTeamList = new ArrayList<>();
        List<TeamDTO> teamList = teamRepository.findPagingTeam(searchTeamDTO);
        if(teamList.isEmpty())
            teamList = Collections.emptyList();

        teamList.forEach(team -> {
            Long teamSeq = team.getTeamSeq();
            List<TeamRegularExercise> exerciseList = teamRegularExerciseRepository.findByTeamSeq(teamSeq);
            TeamDTO teamDTO = new TeamDTO()
                    .teamSeq(team.getTeamSeq())
                    .leaderId(team.getLeaderId())
                    .teamName(team.getTeamName())
                    .teamImagePath(team.getTeamImagePath())
                    .hometown(team.getHometown())
                    .introduction(team.getIntroduction())
                    .foundationYmd(team.getFoundationYmd())
                    .regDate(team.getRegDate())
                    .updateDate(team.getUpdateDate())
                    .sidoCode(team.getSidoCode())
                    .sigunguCode(team.getSigunguCode())
                    .totMember(team.getTotMember())
                    .teamRegularExercisesList(exerciseList.isEmpty() ? Collections.emptyList() : exerciseList);

            resultTeamList.add(teamDTO);
        });

        return resultTeamList.isEmpty() ?
                Collections.emptyList() : resultTeamList;
    }

    /**
     * 팀 생성
     * @return Long
     */
    @Transactional
    public Team createTeam(Long userSeq, TeamDTO teamDTO) {
        Team team = Team.builder()
                .teamName(teamDTO.getTeamName())
                .hometown(teamDTO.getHometown())
                .foundationYmd(teamDTO.getFoundationYmd())
                .introduction(teamDTO.getIntroduction())
                .teamImagePath(teamDTO.getTeamImagePath())
                .leaderId(userSeq)
                .regDate(LocalDate.now(ZoneId.of("Asia/Seoul")))
                .updateDate(LocalDate.now(ZoneId.of("Asia/Seoul")))
                .build();
        teamRepository.saveTeam(team);

        List<TeamRegularExercise> teamRegularExerciseList = teamDTO.getTeamRegularExercisesList();
        teamRegularExerciseList.forEach(tempDTO -> {
            TeamRegularExercise teamRegularExercise = TeamRegularExercise.builder()
                    .teamSeq(team.getTeamSeq())
                    .startTime(tempDTO.getStartTime())
                    .endTime(tempDTO.getEndTime())
                    .dayOfTheWeekCode(tempDTO.getDayOfTheWeekCode())
                    .exercisePlaceAddress(tempDTO.getExercisePlaceAddress())
                    .exercisePlaceName(tempDTO.getExercisePlaceName())
                    .build();
            teamRegularExerciseRepository.saveTeamRegularExercise(teamRegularExercise);
        });

        return team;
    }
}
