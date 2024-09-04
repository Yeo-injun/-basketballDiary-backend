package com.threeNerds.basketballDiary.mvc.myTeam.service;

import com.threeNerds.basketballDiary.file.ImageUploader;
import com.threeNerds.basketballDiary.file.Uploader;
import com.threeNerds.basketballDiary.mvc.myTeam.domain.TeamMember;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.FindMyTeamProfileDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.ProfileDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.modifyMyTeamProfile.request.ModifyMyTeamProfileRequest;
import com.threeNerds.basketballDiary.mvc.myTeam.repository.MyTeamRepository;
import com.threeNerds.basketballDiary.mvc.myTeam.repository.TeamMemberRepository;
import com.threeNerds.basketballDiary.mvc.myTeam.service.dto.ProfileQuery;
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
     * Repository
     **--------------------------------------*/
    private final TeamMemberRepository teamMemberRepository;
    private final MyTeamRepository myTeamRepository;

    /**--------------------------------------
     * Components
     **--------------------------------------*/
    private final Uploader imageUploader;

    /**
     * 소속팀 프로필 조회
     * @return
     */
    public ProfileQuery.Result getProfile( ProfileQuery query ) {
        ProfileDTO profileParam = ProfileDTO.ofParam( query.getUserSeq(), query.getTeamSeq() );
        ProfileDTO profile = myTeamRepository.findProfileByUserSeqAndTeamSeq( profileParam );
        return query.buildResult( profile );
    }

    /**
     * 소속팀 프로필 수정
     * - 소속팀의 등번호와 프로필 사진 수정
     * - 프로필 사진이 존재하는 경우 기존 프로필 사진 삭제 후 업로드
     * @return
     */
    public void modifyProfile( ModifyMyTeamProfileRequest reqBody ) {
        TeamMember teamMember = teamMemberRepository.findTeamMember( TeamMember.builder()
                                                                        .userSeq( reqBody.getUserSeq() )
                                                                        .teamSeq( reqBody.getTeamSeq() )
                                                                        .build() );

        String imagePath = imageUploader.upload( ImageUploader.Path.PROFILE_THUMBNAIL, reqBody.getImageFile() );

        teamMemberRepository.updateMyTeamProfile( TeamMember.builder()
                                                    .teamMemberSeq(     teamMember.getTeamMemberSeq() )
                                                    .backNumber(        reqBody.getBackNumber() )
                                                    .memberImagePath(   "".equals( imagePath ) ? teamMember.getMemberImagePath() : imagePath )
                                                    .build() );

        // TODO 기존 이미지를 삭제하는 로직은 별도 배치 만들어서 돌리기 ( 적재하는 API구현하기 )
        // 삭제대상 URL를 적재하여 별도 삭제 시행 - 서비스내에서 이미지 파일 삭제를 수행하지 않음)

    }

    // 소속팀 탈퇴하기
    public void removeProfile( FindMyTeamProfileDTO userDto ) {
        // TODO 테스트 필요
        TeamMember deleteTeamMember = TeamMember.builder()
                                        .userSeq( userDto.getUserSeq() )
                                        .teamSeq( userDto.getTeamSeq() )
                                        .build();
        teamMemberRepository.deleteTeamMemberByUserSeqAndTeamSeq( deleteTeamMember );
    }
}
