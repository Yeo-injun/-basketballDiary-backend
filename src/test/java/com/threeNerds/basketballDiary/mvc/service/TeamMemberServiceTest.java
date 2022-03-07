package com.threeNerds.basketballDiary.mvc.service;

import com.threeNerds.basketballDiary.mvc.controller.ProfileController;
import com.threeNerds.basketballDiary.mvc.dto.ResponseMyTeamProfileDTO;
import com.threeNerds.basketballDiary.mvc.repository.TeamMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Transactional
class TeamMemberServiceTest {

    MockHttpSession session;

    @InjectMocks
    private TeamMemberService teamMemberService;
    @Mock
    private TeamMemberRepository teamMemberRepository;

    ProfileController.FindMyTeamProfileDTO findMyTeamProfileDTO;

    ProfileController.ModifyMyTeamProfileDTO modifyMyTeamProfileDTO;

    ResponseMyTeamProfileDTO responseMyTeamProfileDTO;

    @BeforeEach
    void setUpEach(){
        findMyTeamProfileDTO = new ProfileController.FindMyTeamProfileDTO()
                .userSeq(1L)
                .teamSeq(3L);

        modifyMyTeamProfileDTO = new ProfileController.ModifyMyTeamProfileDTO()
                .findMyTeamProfileDTO(findMyTeamProfileDTO)
                .backNumber("11");

        responseMyTeamProfileDTO = new ResponseMyTeamProfileDTO()
                .responseTeamName("Bird")
                .responseBackNumber("13")
                .responseUserName("Jodan");

        session = new MockHttpSession();
    }
    @Test
    void findProfile(){
        //given
        when(teamMemberRepository.findMyTeamProfile(findMyTeamProfileDTO)).thenReturn(responseMyTeamProfileDTO);
        //when
        ResponseMyTeamProfileDTO profile = teamMemberService.findProfile(findMyTeamProfileDTO);
        //then
        assertThat(profile).isEqualTo(responseMyTeamProfileDTO);
    }
    @Test
    void updateProfile(){
        //given
        doNothing().when(teamMemberRepository).updateMyTeamProfile(modifyMyTeamProfileDTO);
        //when
        teamMemberService.updateMyTeamProfile(modifyMyTeamProfileDTO);
        //then
        verify(teamMemberRepository).updateMyTeamProfile(modifyMyTeamProfileDTO);
    }

}