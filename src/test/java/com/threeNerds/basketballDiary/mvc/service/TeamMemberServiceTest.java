package com.threeNerds.basketballDiary.mvc.service;

import com.threeNerds.basketballDiary.mvc.controller.MyTeamController;
import com.threeNerds.basketballDiary.mvc.dto.myTeam.FindMyTeamProfileDTO;
import com.threeNerds.basketballDiary.mvc.dto.myTeam.ModifyMyTeamProfileDTO;
import com.threeNerds.basketballDiary.mvc.dto.myTeam.ResponseMyTeamProfileDTO;
import com.threeNerds.basketballDiary.mvc.dto.myTeam.myTeam.MemberDTO;
import com.threeNerds.basketballDiary.mvc.repository.TeamMemberRepository;
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

    FindMyTeamProfileDTO findMyTeamProfileDTO;

    ModifyMyTeamProfileDTO modifyMyTeamProfileDTO;

    ResponseMyTeamProfileDTO responseMyTeamProfileDTO;

    @BeforeEach
    void setUpEach(){
        FindMyTeamProfileDTO findMyTeamProfileDTO = new FindMyTeamProfileDTO();
        findMyTeamProfileDTO = new FindMyTeamProfileDTO()
                .userSeq(1L)
                .teamSeq(3L);

        modifyMyTeamProfileDTO = new ModifyMyTeamProfileDTO()
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
        //ResponseMyTeamProfileDTO profile = teamMemberService.findProfile(findMyTeamProfileDTO);
        MemberDTO profile = teamMemberService.findProfile(findMyTeamProfileDTO);
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