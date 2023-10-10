package com.threeNerds.basketballDiary.mvc.myTeam.service;

import com.threeNerds.basketballDiary.file.Uploader;
import com.threeNerds.basketballDiary.mvc.myTeam.domain.TeamMember;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.FindMyTeamProfileDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.getMyTeamProfile.request.GetMyTeamProfileRequest;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.getMyTeamProfile.response.MyTeamProfileDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.modifyMyTeamProfile.request.ModifyMyTeamProfileRequest;
import com.threeNerds.basketballDiary.mvc.myTeam.repository.TeamMemberRepository;
import com.threeNerds.basketballDiary.mvc.myTeam.repository.MyTeamRepository;
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

    /**
     * DI원리
     * - 스프링 컨테이너 있는 스프링 Bean들을 Spring에서 자동으로 주입해줌.
     * - @Autowired로 주입대상을 선언하면 해당되는 Bean을 자동으로 주입시켜줌
     *      1. @Autowired는 기본적으로 Type을 기반으로 주입한다. ( Class or Interface )
     *      2. Type기반 주입시 2개 이상의 Type이 존재할 경우 fieldName과 일치하는 Bean Name을 가진 Bean을 주입한다.
     * - 그 이외에 Bean 주입의 우선순위 제어
     *      1. @Primary 어노테이션을 통해 특정 Type의 Bean을 우선순위로 지정
     *      2. @Qualifier로 주입하고자 하는 Bean을 명시적으로 선언할 수 있음
     * - 명시적으로 제어하는 방식을 지향 ( @Primary는 사용지양 1순위 @Autowired 필드기반 주입 / 2순위 @Qualifier 한정자 사용 )
     */
    private final Uploader imageUploader;

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

        String imagePath = imageUploader.upload( "/myTeams/profile", reqBody.getImageFile() );

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
