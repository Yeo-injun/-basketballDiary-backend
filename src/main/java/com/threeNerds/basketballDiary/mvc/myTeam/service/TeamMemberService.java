package com.threeNerds.basketballDiary.mvc.myTeam.service;

import com.threeNerds.basketballDiary.file.PathManager;
import com.threeNerds.basketballDiary.file.Uploader;
import com.threeNerds.basketballDiary.file.ImagePathManager;
import com.threeNerds.basketballDiary.mvc.myTeam.domain.TeamMember;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.FindMyTeamProfileDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.getMyTeamProfile.request.GetMyTeamProfileRequest;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.getMyTeamProfile.response.MyTeamProfileDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.modifyMyTeamProfile.request.ModifyMyTeamProfileRequest;
import com.threeNerds.basketballDiary.mvc.myTeam.repository.TeamMemberRepository;
import com.threeNerds.basketballDiary.mvc.myTeam.repository.MyTeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *  Service
 * @author 책임자 작성
 *
 * issue and history
 * <pre>
 * 2022.02.08 여인준 : 소스코드 생성
 * </pre>
 */

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class TeamMemberService {

    /**--------------------------------------
     * Repository
     **--------------------------------------*/
    private final TeamMemberRepository teamMemberRepository;
    private final MyTeamRepository myTeamRepository;

    /**--------------------------------------
     * Components
     **--------------------------------------*/

    @Qualifier("imageUploder")
    private final Uploader imageUploader;
    private final ImagePathManager imagePathManager;

    // 2022.05.08. 강창기   소속팀 프로필 api와 프로필 수정조회 api와 함께 사용하기 위해 수정반영
    /**
     * 소속팀 프로필 조회
     * @return
     */
    public MyTeamProfileDTO getMyTeamProfile( GetMyTeamProfileRequest reqBody ) {
        MyTeamProfileDTO profileParam = new MyTeamProfileDTO()
                                            .userSeq( reqBody.getUserSeq() )
                                            .teamSeq( reqBody.getTeamSeq() );
        MyTeamProfileDTO myProfile = myTeamRepository.findProfileByUserSeqAndTeamSeq( profileParam );
        myProfile.setAllCodeName();
        return myProfile;
    }

    /**
     * 소속팀 프로필 수정
     * - 소속팀의 등번호와 프로필 사진 수정
     * - 프로필 사진이 존재하는 경우 기존 프로필 사진 삭제 후 업로드
     * @return
     */
    public int modifyMyTeamProfile( ModifyMyTeamProfileRequest reqBody ) {
        TeamMember teamMember = teamMemberRepository.findTeamMember( TeamMember.builder()
                                                                        .userSeq( reqBody.getUserSeq() )
                                                                        .teamSeq( reqBody.getTeamSeq() )
                                                                        .build() );

        // TODO 기존 이미지를 삭제하는 로직은 데이터 처리 후로 옮기기 ( 트랜잭션 고려 ... )
        String prevImagePath = teamMember.getMemberImagePath();
        if ( !( null == prevImagePath || "".equals( prevImagePath ) ) ) {
            // TODO 업로드된 이미지 삭제
        }

        String imagePath = imageUploader.upload(
                imagePathManager.makeDirWithYmdPattern( "/myTeams/profile" )
                , reqBody.getImageFile()
        );

        TeamMember updatedTeamMember = TeamMember.builder()
                                            .teamMemberSeq( teamMember.getTeamMemberSeq() )
                                            .backNumber( reqBody.getBackNumber() )
                                            .memberImagePath( imagePath )
                                            .build();

        return teamMemberRepository.updateMyTeamProfile( updatedTeamMember );
    }

    public void deleteMyTeamProfile(FindMyTeamProfileDTO userDto){
        teamMemberRepository.deleteMyTeamProfile(userDto);
    }
}
