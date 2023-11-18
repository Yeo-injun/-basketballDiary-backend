package com.threeNerds.basketballDiary.mvc.team.service;

import com.threeNerds.basketballDiary.file.ImageUploader;
import com.threeNerds.basketballDiary.file.Uploader;
import com.threeNerds.basketballDiary.mvc.team.controller.request.RegisterTeamRequest;
import com.threeNerds.basketballDiary.mvc.team.controller.response.SearchTeamsResponse;
import com.threeNerds.basketballDiary.mvc.team.domain.Team;
import com.threeNerds.basketballDiary.mvc.myTeam.domain.TeamMember;
import com.threeNerds.basketballDiary.mvc.team.domain.TeamRegularExercise;
import com.threeNerds.basketballDiary.mvc.user.domain.User;
import com.threeNerds.basketballDiary.mvc.team.dto.TeamAuthDTO;
import com.threeNerds.basketballDiary.mvc.team.dto.SearchTeamDTO;
import com.threeNerds.basketballDiary.mvc.team.dto.TeamDTO;
import com.threeNerds.basketballDiary.mvc.team.dto.TeamRegularExerciseDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.repository.TeamMemberRepository;
import com.threeNerds.basketballDiary.mvc.team.repository.TeamRegularExerciseRepository;
import com.threeNerds.basketballDiary.mvc.team.repository.TeamRepository;
import com.threeNerds.basketballDiary.mvc.user.repository.UserRepository;
import com.threeNerds.basketballDiary.pagination.PagerDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
 * 2022..14 강창기 : 팀 목록 조회 구현
 * 2022.03.23 강창기 : 팀 생성 구현
 * </pre>
 */

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class TeamService {

    /**------------------------------------
     * Components
     *-------------------------------------*/
    private final Uploader imageUploader;

    /**------------------------------------
     * Repository
     *-------------------------------------*/
    private final TeamRepository teamRepository;
    private final TeamRegularExerciseRepository teamRegularExerciseRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final UserRepository userRepository;

    /**
     * 팀 목록 조회
     * @return List<TeamDTO>
     */
    public SearchTeamsResponse searchTeams(SearchTeamDTO searchTeamDTO) {
        log.info("TeamService.searchTeams");
        if ( searchTeamDTO.getStartTime() != null
            && searchTeamDTO.getEndTime() != ""
        ) {
            searchTeamDTO
                    .startTime(searchTeamDTO.getStartTime().replace(":", ""))
                    .endTime(searchTeamDTO.getEndTime().replace(":", ""));
        }

        /** 페이징 정보 세팅 */
        PagerDTO pager = new PagerDTO(searchTeamDTO.getPageNo(), 5);
        searchTeamDTO.pagerDTO(pager);

        /** 팀목록 조회 */
        List<TeamDTO> teamSearchResults = teamRepository.findPagingTeam(searchTeamDTO);

        /** 페이징DTO에 조회 결과 세팅 */
        if(teamSearchResults.isEmpty()) {
            pager.setPagingData(0);
            return new SearchTeamsResponse(pager, Collections.emptyList());
        }
        pager.setPagingData(teamSearchResults.get(0).getTotalCount());

        /** 팀들의 정기운동시간 조회 및 세팅 */
        teamSearchResults.forEach(teamDTO -> {
            Long teamSeq = teamDTO.getTeamSeq();
            List<TeamRegularExerciseDTO> exercises = teamRegularExerciseRepository.findByTeamSeq(teamSeq);
            teamDTO.setParsedTeamRegularExercises(exercises);
        });

        return new SearchTeamsResponse(pager, teamSearchResults);
    }

    /**
     * 팀 생성
     * @return Long
     */
    @Transactional
    public List<TeamAuthDTO> createTeam( RegisterTeamRequest teamDTO ) {

        String uploadUrl = imageUploader.upload( ImageUploader.Path.TEAM_LOGO, teamDTO.getTeamLogoImage() );

        /** 팀정보 저장  - seq생성 */
        Team newTeam = Team.create( teamDTO, uploadUrl );
        teamRepository.saveTeam(newTeam);

        /** 팀장 팀멤버로 등록 */
        TeamMember newMember = TeamMember.createLeader( newTeam );
        teamMemberRepository.saveTeamMemeber(newMember);

        /** 팀 정기운동 정보 저장 - 없으면 비어있는 리스트로 처리 */
        Long newTeamSeq = newTeam.getTeamSeq();
        List<TeamRegularExerciseDTO> teamRegularExerciseList = Optional.ofNullable(teamDTO.getTeamRegularExercises())
                                                                        .orElseGet(() -> Collections.emptyList());
        teamRegularExerciseList.forEach(exercise -> {
            TeamRegularExercise newExercise = TeamRegularExercise.create(newTeamSeq, exercise);
            teamRegularExerciseRepository.saveTeamRegularExercise(newExercise);
        });

        /** 변경된 권한정보 조회 */
        User user = new User().builder()
                .userSeq(teamDTO.getLeaderUserSeq())
                .build();
        List<TeamAuthDTO> authList = userRepository.findAuthList(user);
        return authList;
    }
}
