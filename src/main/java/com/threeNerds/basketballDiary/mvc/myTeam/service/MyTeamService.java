package com.threeNerds.basketballDiary.mvc.myTeam.service;

import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.error.DomainErrorType;
import com.threeNerds.basketballDiary.file.ImagePath;
import com.threeNerds.basketballDiary.file.ImageUploader;
import com.threeNerds.basketballDiary.mvc.game.service.dto.TeamMemberQuery;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.*;
import com.threeNerds.basketballDiary.mvc.myTeam.repository.TeamMemberRepository;
import com.threeNerds.basketballDiary.mvc.myTeam.service.dto.MyTeamCommand;
import com.threeNerds.basketballDiary.mvc.myTeam.service.dto.MyTeamInfoCommand;
import com.threeNerds.basketballDiary.mvc.myTeam.service.dto.MyTeamInfoQuery;
import com.threeNerds.basketballDiary.mvc.myTeam.service.dto.MyTeamQuery;
import com.threeNerds.basketballDiary.mvc.team.domain.Team;
import com.threeNerds.basketballDiary.mvc.team.domain.TeamRegularExercise;
import com.threeNerds.basketballDiary.mvc.team.dto.TeamRegularExerciseDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.repository.MyTeamRepository;
import com.threeNerds.basketballDiary.mvc.team.repository.TeamRegularExerciseRepository;
import com.threeNerds.basketballDiary.mvc.team.repository.TeamRepository;
import com.threeNerds.basketballDiary.pagination.Pagination;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

    /**------------------------------------
     * Components
     *-------------------------------------*/
    private final ImageUploader imageUploader;

    /**------------------------------------
     * Repository
     *-------------------------------------*/
    private final MyTeamRepository myTeamRepository;
    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final TeamRegularExerciseRepository teamRegularExerciseRepository;

    /**
     * 소속팀 운영진 목록 조회
     */
    public TeamMemberQuery.Result getManagers( TeamMemberQuery query ) {
        Long teamSeq = query.getTeamSeq();
        List<MemberDTO> managerList = myTeamRepository.findAllManagerByTeamSeq( teamSeq );
        return query.buildResult( managerList );
    }

    /**
     * 소속팀 팀원 목록 조회
     * @param
     * @return List<MemberDTO>
     */
    public TeamMemberQuery.Result getTeamMembers( TeamMemberQuery query ) {
        Pagination pagination = Pagination.of( query.getPageNo() );

        MemberDTO searchMemebrCond = new MemberDTO()
                .teamSeq( query.getTeamSeq() )
                .pagination( pagination );

        // 소속팀은 팀장과 운영진을 제외하므로, 팀원 정보가 존재하지 않더라도 404 처리하지 않는다.
        List<MemberDTO> resultMembers = myTeamRepository.findPagingMemberByTeamSeq( searchMemebrCond );

        /** 페이징DTO에 조회 결과 세팅 */
        if ( resultMembers.isEmpty() ) {
            return query.buildResult( pagination.empty(), Collections.emptyList() );
        }
        return query.buildResult( pagination.getPages( resultMembers.get(0).getTotalCount() ), resultMembers );
    }

    public TeamMemberQuery.Result searchAllTeamMembers( TeamMemberQuery query ) {
        // TODO 화면에서 페이징 처리가 안되어 있음.
        Pagination pagination = Pagination.of( query.getPageNo() );
        MemberDTO searchMemberCond = new MemberDTO()
                                        .teamSeq(       query.getTeamSeq() )
                                        .pagination(    pagination )
                                        .userName(      query.getPlayerName() );

        List<MemberDTO> resultMembers = myTeamRepository.findAllTeamMemberPaging( searchMemberCond );

        /** 페이징DTO에 조회 결과 세팅 */
        if ( resultMembers.isEmpty() ) {
            return query.buildResult( pagination.empty(), Collections.emptyList() );
        }
        return query.buildResult( pagination.getPages( resultMembers.get(0).getTotalCount() ), resultMembers );
    }

    /**
     * 소속팀 목록 조회
     */
    public MyTeamQuery.Result getMyTeams( MyTeamQuery query ) {
        /** 페이징 정보 세팅 */
        Pagination pagination = Pagination.of( query.getPageNo(), 3 );
        SearchMyTeamDTO searchTeamParam = new SearchMyTeamDTO()
                                                .userSeq( query.getUserSeq() )
                                                .pagination( pagination );

        /** 소속팀 목록 조회 */
        List<MyTeamDTO> myTeams = myTeamRepository.findPagingMyTeams( searchTeamParam );

        /** 페이징DTO에 조회 결과 세팅 */
        if ( myTeams.isEmpty()) {
            return query.buildResult( Collections.emptyList(), pagination.empty() );
        }
        /** 팀들의 정기운동시간 조회 및 세팅 */
        myTeams.forEach( myTeamInfo -> {
            List<TeamRegularExerciseDTO> exercises = teamRegularExerciseRepository.findByTeamSeq( myTeamInfo.getTeamSeq() );
            myTeamInfo.setTeamRegularExercises( exercises );
        });

        return query.buildResult( myTeams, pagination.getPages( myTeams.get(0).getTotalCount() ) );
    }

    /**
     * 소속팀 단건 조회
     */
    public MyTeamInfoQuery.Result getMyTeamInfo( MyTeamInfoQuery query ) {
        TeamInfoDTO teamInfo = myTeamRepository.findByUserSeqAndTeamSeq( new FindTeamInfoDTO( query.getTeamSeq(), query.getUserSeq() ) );
        boolean assignedTeam = null != teamInfo;
        if ( !assignedTeam ) {
            throw new CustomException( DomainErrorType.NOT_FOUND_ASSIGNED_TEAM );
        }
        List<TeamRegularExerciseDTO> regularExercises = teamRegularExerciseRepository.findByTeamSeq( query.getTeamSeq() );
        return query.buildResult( teamInfo, regularExercises );
    }

    /**
     * 소속팀 정보 수정
     */
    public void modifyMyTeamInfo( MyTeamInfoCommand command ) {

        Long teamSeq            = command.getTeamSeq();
        TeamInfoDTO teamInfo    = command.getTeamInfo();
        List<TeamRegularExerciseDTO> paramExercises = command.getTeamRegularExercises();

        /**----------------------
         * 팀정보 수정
         **----------------------*/
        // 팀 존재여부 확인
        Team team = teamRepository.findByTeamSeq( teamSeq );
        if ( null == team ) {
            throw new CustomException( DomainErrorType.NOT_FOUND_ASSIGNED_TEAM );
        }
        // 팀 로고 업로드
        String imageUploadPath = imageUploader.upload( ImagePath.Type.TEAM_LOGO, command.getTeamLogoImage() );
        // 팀 정보 update
        teamRepository.updateTeam( team.ofUpdate( teamInfo, imageUploadPath ) );

        /**----------------------
         * 정기운동시간 수정
         **----------------------*/
        if ( paramExercises.isEmpty() ) {
            return;
        }
        // 기존 정기운동시간 삭제
        teamRegularExerciseRepository.deleteAllByTeamSeq( teamSeq );
        // 화면에서 입력한 정기운동시간 insert
        paramExercises.stream()
            .map( dto -> {
                return TeamRegularExercise.builder()
                        .teamSeq(           teamSeq )
                        .dayOfTheWeekCode(  dto.getDayOfTheWeekCode() )
                        .startTime(         dto.getStartTime() )
                        .endTime(           dto.getEndTime() )
                        .exercisePlaceName( dto.getExercisePlaceName() )
                        .exercisePlaceAddress( dto.getExercisePlaceAddress() )
                        .build();
            })
            .forEach( teamRegularExerciseRepository::saveTeamRegularExercise );
    }

    /**
     * 소속팀 삭제(팀 삭제와 동일)
     */
    public void removeMyTeam( MyTeamCommand command ) {
        Long teamSeq = command.getTeamSeq();
        Team team = teamRepository.findByTeamSeq( teamSeq );
        if ( null == team ) {
            throw new CustomException( DomainErrorType.NOT_FOUND_TEAM_INFO );
        }
        if ( !team.isTeamLeader( command.getLeaderUserSeq() ) ) {
            throw new CustomException( DomainErrorType.ONLY_REMOVE_TEAM_BY_LEADER );
        }
        /**
         * 팀정보와 팀원정보 모두 삭제처리
         * TODO 향후 데이터 정합성 유지를 위해 삭제 flag로 관리하는 방식 검토
         */
        teamRepository.deleteById( teamSeq );
        teamMemberRepository.deleteAllByTeamSeq( teamSeq );
    }

}