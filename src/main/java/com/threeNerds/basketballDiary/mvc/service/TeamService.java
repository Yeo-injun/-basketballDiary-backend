package com.threeNerds.basketballDiary.mvc.service;

import com.threeNerds.basketballDiary.mvc.domain.Team;
import com.threeNerds.basketballDiary.mvc.domain.TeamMember;
import com.threeNerds.basketballDiary.mvc.domain.TeamRegularExercise;
import com.threeNerds.basketballDiary.mvc.domain.User;
import com.threeNerds.basketballDiary.mvc.dto.TeamAuthDTO;
import com.threeNerds.basketballDiary.mvc.dto.team.team.SearchTeamDTO;
import com.threeNerds.basketballDiary.mvc.dto.team.team.TeamDTO;
import com.threeNerds.basketballDiary.mvc.repository.TeamMemberRepository;
import com.threeNerds.basketballDiary.mvc.repository.TeamRegularExerciseRepository;
import com.threeNerds.basketballDiary.mvc.repository.TeamRepository;
import com.threeNerds.basketballDiary.mvc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final TeamMemberRepository teamMemberRepository;
    private final UserRepository userRepository;

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

        List<TeamDTO> teamSearchResults = teamRepository.findPagingTeam(searchTeamDTO);
        if(teamSearchResults.isEmpty())
            teamSearchResults = Collections.emptyList();

        teamSearchResults.forEach(teamDTO -> {
            Long teamSeq = teamDTO.getTeamSeq();
            List<TeamRegularExercise> exercises = teamRegularExerciseRepository.findByTeamSeq(teamSeq);
            teamDTO.teamRegularExercisesList(exercises.isEmpty() ? Collections.emptyList() : exercises);
        });

        return teamSearchResults;
    }

    /**
     * 팀 생성
     * @return Long
     */
    @Transactional
    public List<TeamAuthDTO> createTeam(TeamDTO teamDTO)
    {
        /** 팀정보 저장  - seq생성 */
        Team newTeam = Team.create(teamDTO);
        teamRepository.saveTeam(newTeam);

        /** 팀장 팀멤버로 등록 */
        TeamMember newMember = TeamMember.createLeader(newTeam);
        teamMemberRepository.saveTeamMemeber(newMember);

        /** 팀 정기운동 정보 저장 */
        Long newTeamSeq = newTeam.getTeamSeq();
        List<TeamRegularExercise> teamRegularExerciseList = teamDTO.getTeamRegularExerciseList();
        teamRegularExerciseList.forEach(exercise -> {
            TeamRegularExercise newExercise = TeamRegularExercise.create(newTeamSeq, exercise);
            teamRegularExerciseRepository.saveTeamRegularExercise(newExercise);
        });

        /** 변경된 권한정보 조회 */
        User user = new User().builder()
                .userSeq(teamDTO.getLeaderId())
                .build();
        List<TeamAuthDTO> authList = userRepository.findAuthList(user);
        return authList;
    }
}
