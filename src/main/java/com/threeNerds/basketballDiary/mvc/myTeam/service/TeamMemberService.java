package com.threeNerds.basketballDiary.mvc.myTeam.service;

import com.threeNerds.basketballDiary.mvc.myTeam.dto.FindMyTeamProfileDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.ModifyMyTeamProfileDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.MemberDTO;
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
    private final TeamMemberRepository teamMemberRepository;
    private final MyTeamRepository myTeamRepository;

    // 2022.05.08. 강창기   소속팀 프로필 api와 프로필 수정조회 api와 함께 사용하기 위해 수정반영
    public MemberDTO findProfile(FindMyTeamProfileDTO userDto){
        MemberDTO myProfile = myTeamRepository.findProfileByUserSeqAndTeamSeq(userDto);
        myProfile.setAllCodeName();
        return myProfile;
    }

    public int updateMyTeamProfile(ModifyMyTeamProfileDTO userDto){
        return teamMemberRepository.updateMyTeamProfile(userDto);
    }

    public void deleteMyTeamProfile(FindMyTeamProfileDTO userDto){
        teamMemberRepository.deleteMyTeamProfile(userDto);
    }
}
