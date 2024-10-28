package com.threeNerds.basketballDiary.mvc.team.service;

import com.threeNerds.basketballDiary.file.ImagePath;
import com.threeNerds.basketballDiary.file.ImageUploader;
import com.threeNerds.basketballDiary.mvc.team.domain.Team;
import com.threeNerds.basketballDiary.mvc.team.domain.TeamRegularExercise;

import com.threeNerds.basketballDiary.mvc.team.mapper.TeamRegularExerciseMapper;
import com.threeNerds.basketballDiary.mvc.team.mapper.dto.SearchTeamDTO;
import com.threeNerds.basketballDiary.mvc.team.mapper.dto.TeamDTO;
import com.threeNerds.basketballDiary.mvc.team.mapper.dto.TeamRegularExerciseDTO;
import com.threeNerds.basketballDiary.mvc.team.domain.repository.TeamMemberRepository;
import com.threeNerds.basketballDiary.mvc.team.domain.repository.TeamRegularExerciseRepository;
import com.threeNerds.basketballDiary.mvc.team.domain.repository.TeamRepository;
import com.threeNerds.basketballDiary.mvc.team.mapper.TeamMapper;
import com.threeNerds.basketballDiary.mvc.team.service.dto.TeamCommand;
import com.threeNerds.basketballDiary.mvc.team.service.dto.TeamQuery;
import com.threeNerds.basketballDiary.pagination.Pagination;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /**------------------------------------
     * Components
     *-------------------------------------*/
    private final ImageUploader imageUploader;

    /**------------------------------------
     * Repository
     *-------------------------------------*/
    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final TeamRegularExerciseRepository teamRegularExerciseRepository;


    /**------------------------------------
     * Mapper
     *-------------------------------------*/
    private final TeamMapper teamMapper;
    private final TeamRegularExerciseMapper teamRegularExerciseMapper;

    /**
     * 팀 목록 조회
     * @return List<TeamDTO>
     */
    public TeamQuery.Result searchTeams( TeamQuery query ) {

        /** 페이징 정보 세팅 */
        Pagination pagination = Pagination.of( query.getPageNo(), 5 );
        SearchTeamDTO searchTeamDTO = new SearchTeamDTO(
                query.getTeamName(), query.getSigungu(),
                query.getStartDay(), query.getEndDay(),
                query.getStartTime(), query.getEndTime(),
                pagination
        );

        /** 팀목록 조회 */
        List<TeamDTO> teamSearchResults = teamMapper.findPaginationTeamInfo( searchTeamDTO );

        /** 페이징DTO에 조회 결과 세팅 */
        if ( teamSearchResults.isEmpty() ) {
            return query.buildResult( pagination.empty(), Collections.emptyList() );
        }

        /** 팀들의 정기운동시간 조회 및 세팅 */
        teamSearchResults.forEach( teamDTO -> {
            Long teamSeq = teamDTO.getTeamSeq();
            teamDTO.setTeamRegularExercises( teamRegularExerciseMapper.findAllExerciseByTeamSeq( teamSeq ) );
        });

        return query.buildResult( pagination.getPages( teamMapper.findTotalCountTeamInfo( searchTeamDTO ) ), teamSearchResults );
    }

    /**
     * 팀 생성
     * @return Long
     */
    public void createTeam( TeamCommand command ) {

        String uploadUrl = imageUploader.upload( ImagePath.Type.TEAM_LOGO, command.getTeamLogoImage() );

        /** 팀정보 저장  - seq생성 */
        Team newTeam = command.createTeam( uploadUrl );
        teamRepository.saveTeam( newTeam );

        /** 팀장 등록 */
        teamMemberRepository.saveTeamMember( newTeam.createLeader() );

        /** 팀 정기운동 정보 저장 - 없으면 비어있는 리스트로 처리 */
        Long newTeamSeq = newTeam.getTeamSeq();
        List<TeamRegularExerciseDTO> teamRegularExerciseList = command.getTeamRegularExercises();
        teamRegularExerciseList.forEach( exercise -> {
            teamRegularExerciseRepository.saveTeamRegularExercise(
                TeamRegularExercise.builder()
                        .teamSeq(               newTeamSeq )
                        .dayOfTheWeekCode(      exercise.getDayOfTheWeekCode() )
                        .startTime(             exercise.getStartTime() )
                        .endTime(               exercise.getEndTime() )
                        .exercisePlaceAddress(  exercise.getExercisePlaceAddress() )
                        .exercisePlaceName(     exercise.getExercisePlaceName() )
                        .build()
            );
        });
    }
}
