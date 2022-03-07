package com.threeNerds.basketballDiary.mvc.service;

import com.threeNerds.basketballDiary.mvc.controller.ProfileController;
import com.threeNerds.basketballDiary.mvc.controller.UserController;
import com.threeNerds.basketballDiary.mvc.domain.User;
import com.threeNerds.basketballDiary.mvc.dto.ResponseMyTeamProfileDTO;
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

    public ResponseMyTeamProfileDTO findProfile(ProfileController.FindMyTeamProfileDTO userDto){
        return teamMemberRepository.findMyTeamProfile(userDto);
    }

    public void updateMyTeamProfile(ProfileController.ModifyMyTeamProfileDTO userDto){
        teamMemberRepository.updateMyTeamProfile(userDto);
    }

    public void deleteMyTeamProfile(ProfileController.FindMyTeamProfileDTO userDto){
        teamMemberRepository.deleteMyTeamProfile(userDto);
    }
}
