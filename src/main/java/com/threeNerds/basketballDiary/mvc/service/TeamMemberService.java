package com.threeNerds.basketballDiary.mvc.service;

import com.threeNerds.basketballDiary.mvc.controller.MyTeamController;
import com.threeNerds.basketballDiary.mvc.dto.myTeam.FindMyTeamProfileDTO;
import com.threeNerds.basketballDiary.mvc.dto.myTeam.ModifyMyTeamProfileDTO;
import com.threeNerds.basketballDiary.mvc.dto.myTeam.ResponseMyTeamProfileDTO;
import com.threeNerds.basketballDiary.mvc.repository.TeamMemberRepository;
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

    public ResponseMyTeamProfileDTO findProfile(FindMyTeamProfileDTO userDto){
        return teamMemberRepository.findMyTeamProfile(userDto);
    }

    public int updateMyTeamProfile(ModifyMyTeamProfileDTO userDto){
        return teamMemberRepository.updateMyTeamProfile(userDto);
    }

    public void deleteMyTeamProfile(FindMyTeamProfileDTO userDto){
        teamMemberRepository.deleteMyTeamProfile(userDto);
    }
}
