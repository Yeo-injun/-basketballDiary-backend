package com.threeNerds.basketballDiary.mvc.myTeam.service;

import com.threeNerds.basketballDiary.constant.code.PlayerTypeCode;
import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.error.DomainErrorType;
import com.threeNerds.basketballDiary.file.ImageUploader;
import com.threeNerds.basketballDiary.file.Uploader;
import com.threeNerds.basketballDiary.mvc.myTeam.controller.request.GetMyTeamsRequest;
import com.threeNerds.basketballDiary.mvc.myTeam.controller.request.ModifyMyTeamInfoRequest;
import com.threeNerds.basketballDiary.mvc.myTeam.controller.response.GetMyTeamsResponse;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.getManagers.request.GetManagersRequest;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.getManagers.response.GetManagersResponse;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.getTeamMembers.request.GetTeamMembersRequest;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.getTeamMembers.response.GetTeamMembersResponse;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.searchAllTeamMembers.request.SearchAllTeamMembersRequest;
import com.threeNerds.basketballDiary.mvc.team.domain.Team;
import com.threeNerds.basketballDiary.mvc.team.domain.TeamRegularExercise;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.FindMyTeamProfileDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.MemberDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.MyTeamDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.SearchMyTeamDTO;
import com.threeNerds.basketballDiary.mvc.team.dto.TeamRegularExerciseDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.repository.MyTeamRepository;
import com.threeNerds.basketballDiary.mvc.team.repository.TeamRegularExerciseRepository;
import com.threeNerds.basketballDiary.mvc.team.repository.TeamRepository;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.searchAllTeamMembers.response.SearchAllTeamMembersResponse;
import com.threeNerds.basketballDiary.pagination.Pagination;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

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
    private final Uploader imageUploader;

    /**------------------------------------
     * Repository
     *-------------------------------------*/
    private final MyTeamRepository myTeamRepository;
    private final TeamRepository teamRepository;
    private final TeamRegularExerciseRepository teamRegularExerciseRepository;

    /**
     * 소속팀 운영진 목록 조회
     * @param reqBody
     * @return List<MemberDTO>
     */
    public GetManagersResponse getManagers(GetManagersRequest reqBody) {
        // 소속팀 운영진 정보는 반드시 1건 이상(최소한 팀장이 존재해야함)이어야 하므로,
        // 조회내역이 존재하지 않으면 200 처리후 메시지를 전달한다.

        Long teamSeq = reqBody.getTeamSeq();
        List<MemberDTO> resultManagerList
                = myTeamRepository.findAllManagerByTeamSeq(teamSeq);

        resultManagerList = resultManagerList.stream()
                .map(MemberDTO::setAllCodeName)
                .collect(Collectors.toList());

        return new GetManagersResponse(resultManagerList);
    }

    /**
     * 소속팀 팀원 목록 조회
     * @param
     * @return List<MemberDTO>
     */
    public GetTeamMembersResponse getTeamMembers( GetTeamMembersRequest reqBody ) {
        Pagination pagination = Pagination.of( reqBody.getPageNo(), 5 );

        MemberDTO searchMemebrCond = new MemberDTO()
                .teamSeq( reqBody.getTeamSeq() )
                .pagination( pagination );

        // 소속팀은 팀장과 운영진을 제외하므로, 팀원 정보가 존재하지 않더라도 404 처리하지 않는다.
        List<MemberDTO> resultMembers = myTeamRepository.findPagingMemberByTeamSeq(searchMemebrCond);

        /** 페이징DTO에 조회 결과 세팅 */
        if(resultMembers.isEmpty()) {
            return new GetTeamMembersResponse( pagination.empty(), Collections.emptyList());
        }

        resultMembers.stream()
                .map(MemberDTO::setAllCodeName)
                .collect(Collectors.toList());

        return new GetTeamMembersResponse( pagination.getPages( resultMembers.get(0).getTotalCount() ), resultMembers);
    }

    public SearchAllTeamMembersResponse searchAllTeamMembers( SearchAllTeamMembersRequest reqBody ) {
        Pagination pagination = Pagination.of( reqBody.getPageNo() );
        MemberDTO searchMemebrCond = new MemberDTO()
                                        .teamSeq(       reqBody.getTeamSeq() )
                                        .pagination(    pagination )
                                        .userName(      reqBody.getPlayerName() );

        List<MemberDTO> resultMembers = myTeamRepository.findAllTeamMemberPaging(searchMemebrCond);

        /** 페이징DTO에 조회 결과 세팅 */
        if(resultMembers.isEmpty()) {
            return new SearchAllTeamMembersResponse( pagination.empty(), Collections.emptyList());
        }

        resultMembers.stream()
                .map(MemberDTO::setAllCodeName)
                .map( m -> m.setPlayerType(PlayerTypeCode.TEAM_MEMBER))
                .collect(Collectors.toList());

        return new SearchAllTeamMembersResponse( pagination.getPages( resultMembers.get(0).getTotalCount() ), resultMembers );
    }

    /**
     * 소속팀 목록 조회
     * @param reqBody
     * @return List<MyTeamDTO>
     */
    public GetMyTeamsResponse findTeams( GetMyTeamsRequest reqBody ) {
        /** 페이징 정보 세팅 */
        Pagination pagination = Pagination.of( reqBody.getPageNo(), 3 );
        SearchMyTeamDTO searchTeamParam = new SearchMyTeamDTO()
                                                .userSeq( reqBody.getUserSeq() )
                                                .pagination( pagination );

        /** 소속팀 목록 조회 */
        List<MyTeamDTO> myTeams = myTeamRepository.findPagingMyTeams( searchTeamParam );

        /** 페이징DTO에 조회 결과 세팅 */
        if ( myTeams.isEmpty()) {
            return new GetMyTeamsResponse( pagination.empty(), Collections.emptyList() );
        }
        /** 팀들의 정기운동시간 조회 및 세팅 */
        myTeams.forEach( myTeamInfo -> {
            List<TeamRegularExerciseDTO> exercises = teamRegularExerciseRepository.findByTeamSeq( myTeamInfo.getTeamSeq() );
            myTeamInfo.setParsedTeamRegularExercises( exercises );
        });

        return new GetMyTeamsResponse( pagination.getPages( myTeams.get(0).getTotalCount() ), myTeams );
    }

    /**
     * 소속팀 단건 조회
     * @param paramDTO
     * @return MyTeamDTO
     */
    public MyTeamDTO findTeam(FindMyTeamProfileDTO paramDTO) {
        // 소속되지 않은 팀에 대한 조회는 Interceptor에 의해 처리됨.

        MyTeamDTO myTeam = myTeamRepository.findByUserSeqAndTeamSeq(paramDTO);
        List<TeamRegularExerciseDTO> exercisesDTO
                = teamRegularExerciseRepository.findByTeamSeq(paramDTO.getTeamSeq());

        /**
         * Amazon s3로부터 teamImageUrl컬럼을 통해 이미지를 받아와서 MultipartFile로 받아 front로 던져주기.
         */
        MultipartFile teamImage = null;

        MyTeamDTO resultDTO = new MyTeamDTO()
                .teamSeq(myTeam.getTeamSeq())
                .teamName(myTeam.getTeamName())
                .teamImagePath(myTeam.getTeamImagePath())
                .teamImage(teamImage)
                .hometown(myTeam.getHometown())
                .sidoCode(myTeam.getSidoCode())
                .sigunguCode(myTeam.getSigunguCode())
                .foundationYmd(myTeam.getFoundationYmd())
                .introduction(myTeam.getIntroduction())
                .totMember(myTeam.getTotMember())
                .setParsedTeamRegularExercises(exercisesDTO);

        log.info("teamName = {}", myTeam.getTeamName());
        return resultDTO;
    }

    /**
     * 소속팀 수정
     * @param dto
     */
    public void modifyMyTeamInfo( ModifyMyTeamInfoRequest dto, MultipartFile teamLogo ) {
        // TODO 차후 TeamRegularExcerciseDTO로 수정하기. 임시 처리
        Long teamSeq        = dto.getTeamSeq();
        List<TeamRegularExerciseDTO> paramExerciseList = dto.getTeamRegularExercises();

        /** 1. 팀정보 수정 - 존재여부 검증 > 이미지 존재여부 확인 및 업로드 > 데이터 수정 */
        Team team = Optional.ofNullable(teamRepository.findByTeamSeq(teamSeq))
                .orElseThrow(() -> new CustomException(DomainErrorType.MY_TEAM_NOT_FOUND));

        /** 이미지 업로드 */
        String imageUploadPath = imageUploader.upload( ImageUploader.Path.TEAM_LOGO, teamLogo );

        teamRepository.updateTeam( Team.builder()
                .teamSeq(teamSeq)
                .leaderUserSeq(team.getLeaderUserSeq())
                .teamName(dto.getTeamName())
                .teamImagePath( "".equals( imageUploadPath ) ? team.getTeamImagePath() : imageUploadPath )
                .hometown(dto.getHometown())
                .introduction(dto.getIntroduction())
                .foundationYmd(dto.getFoundationYmd())
                .regDate(team.getRegDate())
                .updateDate(LocalDate.now(ZoneId.of("Asia/Seoul")))
                .sidoCode(dto.getSidoCode())
                .sigunguCode(dto.getSigunguCode())
                .build() );

        /* 2. 정기운동내역 수정 */
        // 실제 db에 저장된 정기운동내역
        List<TeamRegularExerciseDTO> dbExerciseList
                = teamRegularExerciseRepository.findByTeamSeq(teamSeq);
        // Front에서 받아온 정기운동내역
        Map<Long, TeamRegularExerciseDTO> paramExerciseMap =
                paramExerciseList.stream()
                        .collect(Collectors.toMap(TeamRegularExerciseDTO::getTeamRegularExerciseSeq, dvo->dvo));

        // DB내용 기준으로 Front 데이터와 비교
        dbExerciseList.forEach(dbData -> {
            Long dbSeq = dbData.getTeamRegularExerciseSeq();
            TeamRegularExerciseDTO paramData = paramExerciseMap.get(dbSeq);

            if (paramData != null) {
                // Seq가 있으므로 조회 후 수정내역 update
                teamRegularExerciseRepository.updateTeamRegularExercise(TeamRegularExercise.builder()
                        .teamRegularExerciseSeq(dbSeq)
                        .teamSeq(teamSeq)
                        .dayOfTheWeekCode(paramData.getDayOfTheWeekCode())
                        .startTime(paramData.getStartTime())
                        .endTime(paramData.getEndTime())
                        .exercisePlaceAddress(paramData.getExercisePlaceAddress())
                        .exercisePlaceName(paramData.getExercisePlaceName())
                        .build());
                // update했으므로 map에서 해당 정기운동항목 삭제
                paramExerciseMap.remove(dbSeq);
            } else {
                // Front에서 값이 없으면 삭제된 내용임.
                teamRegularExerciseRepository.deleteTeamRegularExercise(dbSeq);
            }
        });
        // Map에 값이 남아있다면 INSERT 하기.
        if(paramExerciseMap.size() > 0) {
            paramExerciseMap.forEach((key, value) -> {
                TeamRegularExercise teamRegularExercise = TeamRegularExercise.builder()
                        .teamSeq(teamSeq)
                        .dayOfTheWeekCode(value.getDayOfTheWeekCode())
                        .startTime(value.getStartTime())
                        .endTime(value.getEndTime())
                        .exercisePlaceAddress(value.getExercisePlaceAddress())
                        .exercisePlaceName(value.getExercisePlaceName())
                        .build();

                teamRegularExerciseRepository.saveTeamRegularExercise(teamRegularExercise);
            });
        }
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
                .orElseThrow(() -> new CustomException(DomainErrorType.MY_TEAM_NOT_FOUND));

        teamRepository.deleteById(teamSeq);
    }

}