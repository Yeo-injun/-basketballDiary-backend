package com.threeNerds.basketballDiary.mvc.myTeam.service;

import com.threeNerds.basketballDiary.auth.constant.AuthLevel;
import com.threeNerds.basketballDiary.auth.constant.AuthType;
import com.threeNerds.basketballDiary.file.ImageUploader;
import com.threeNerds.basketballDiary.file.Uploader;
import com.threeNerds.basketballDiary.mvc.myTeam.domain.TeamMember;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.FindMyTeamProfileDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.getMyTeamProfile.request.GetMyTeamProfileRequest;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.getMyTeamProfile.response.MyTeamProfileDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.modifyMyTeamProfile.request.ModifyMyTeamProfileRequest;
import com.threeNerds.basketballDiary.mvc.myTeam.repository.MyTeamRepository;
import com.threeNerds.basketballDiary.mvc.myTeam.repository.TeamMemberRepository;
import com.threeNerds.basketballDiary.mvc.myTeam.service.dto.TeamAuthDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
public class MyTeamAuthService {

    /**--------------------------------------
     * Repository
     **--------------------------------------*/
    private final TeamMemberRepository teamMemberRepo;
    // TODO TeamMemberManagerService에서 팀원의 권한 관련 제어 메소드 옮겨두기

    public TeamAuthDTO getAllTeamAuthInfo( TeamAuthDTO userInfo ) {

        TeamMember teamMemberParam = TeamMember.builder()
                                        .userSeq( userInfo.getUserSeq() )
                                        .build();
        // 소속된 팀 목록 조회
        List<TeamMember> joinTeamList = teamMemberRepo.findAllJoinTeamsByUserSeq( teamMemberParam );
        Map< Long, AuthLevel > teamAuthMap = joinTeamList.stream()
                                                    .collect( Collectors.toMap(
                                                                TeamMember::getTeamSeq,
                                                                item -> AuthLevel.of( AuthType.TEAM, Integer.parseInt( item.getTeamAuthCode() ) )
                                                            )
                                                );
        return TeamAuthDTO.ofJoinTeam( userInfo.getUserSeq(), teamAuthMap );
    }
}
