package com.threeNerds.basketballDiary.mvc.team.service;

import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.error.DomainErrorType;
import com.threeNerds.basketballDiary.file.ImagePath;
import com.threeNerds.basketballDiary.file.ImageUploader;
import com.threeNerds.basketballDiary.mvc.team.domain.TeamMember;
import com.threeNerds.basketballDiary.mvc.team.mapper.dto.ProfileDTO;
import com.threeNerds.basketballDiary.mvc.team.mapper.TeamMemberMapper;
import com.threeNerds.basketballDiary.mvc.team.domain.repository.TeamMemberRepository;
import com.threeNerds.basketballDiary.mvc.team.service.dto.ProfileCommand;
import com.threeNerds.basketballDiary.mvc.team.service.dto.ProfileQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 *  Service
 * @author 책임자 작성
 *
 * issue and history
 * <pre>
 * 2024.09.04 여인준 : 소스코드 생성
 * </pre>
 */

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MyTeamProfileService {
    /**--------------------------------------
     * Components
     **--------------------------------------*/
    private final ImageUploader imageUploader;

    /**--------------------------------------
     * Repository
     **--------------------------------------*/
    private final TeamMemberRepository teamMemberRepository;

    /**--------------------------------------
     * Mapper
     **--------------------------------------*/
    private final TeamMemberMapper teamMemberMapper;


    /**
     * 소속팀 프로필 조회
     * @return
     */
    public ProfileQuery.Result getProfile( ProfileQuery query ) {
        ProfileDTO profileParam = ProfileDTO.ofParam( query.getUserSeq(), query.getTeamSeq() );
        ProfileDTO profile = teamMemberMapper.findProfileByUserSeqAndTeamSeq( profileParam );
        return query.buildResult( profile );
    }

    /**
     * 소속팀 프로필 수정
     * - 소속팀의 등번호와 프로필 사진 수정
     * - 프로필 사진이 존재하는 경우 기존 프로필 사진 삭제 후 업로드
     * @return
     */
    public void modifyProfile( ProfileCommand command ) {
        TeamMember teamMember = teamMemberRepository.findTeamMember(
                                        TeamMember.builder()
                                            .userSeq( command.getUserSeq() )
                                            .teamSeq( command.getTeamSeq() )
                                            .build()
                                );

        String imagePath = imageUploader.upload( ImagePath.Type.TEAM_PROFILE, command.getProfileImage() );

        teamMemberRepository.updateMyTeamProfile( TeamMember.builder()
                                                    .teamMemberSeq(     teamMember.getTeamMemberSeq() )
                                                    .backNumber(        command.getBackNumber() )
                                                    .memberImagePath(   "".equals( imagePath ) ? teamMember.getMemberImagePath() : imagePath )
                                                    .build() );

        // TODO 기존 이미지를 삭제하는 로직은 별도 배치 만들어서 돌리기 ( 적재하는 API구현하기 )
        // 삭제대상 URL를 적재하여 별도 삭제 시행 - 서비스내에서 이미지 파일 삭제를 수행하지 않음)

    }

    // 소속팀 탈퇴하기
    // => 체크사항 : 팀가입요청, 선수초대시 탈퇴한 이력이 있는 팀인 경우 기존 팀원이력을 승계할 것인지, 아닌지 정의 필요.
    public void withdrawTeam( ProfileCommand command ) {
        TeamMember teamMemberParam = TeamMember.builder()
                                        .userSeq( command.getUserSeq() )
                                        .teamSeq( command.getTeamSeq() )
                                        .build();
        TeamMember teamMember = teamMemberRepository.findTeamMemberByUserAndTeamSeq( teamMemberParam );
        if ( null == teamMember ) {
            throw new CustomException( DomainErrorType.NO_JOIN_TEAM_MEMBER );
        }
        if ( teamMember.checkLeaderAuth() ) {
            throw new CustomException( DomainErrorType.TEAM_LEADER_CANT_WITHDRAW );
        }
        teamMemberRepository.updateWithdrawalState( teamMember.toWithdrawal() );
    }
}
