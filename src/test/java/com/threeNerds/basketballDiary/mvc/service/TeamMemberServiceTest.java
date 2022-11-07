package com.threeNerds.basketballDiary.mvc.service;

import com.threeNerds.basketballDiary.mvc.myTeam.dto.FindMyTeamProfileDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.ModifyMyTeamProfileDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.MemberDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.service.TeamMemberService;
import com.threeNerds.basketballDiary.mvc.myTeam.repository.MyTeamRepository;
import com.threeNerds.basketballDiary.mvc.myTeam.repository.TeamMemberRepository;
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
    @Mock
    private MyTeamRepository myTeamRepository;

    FindMyTeamProfileDTO findMyTeamProfileDTO;

    ModifyMyTeamProfileDTO modifyMyTeamProfileDTO;

    MemberDTO memberDTO;

    @BeforeEach
    void setUpEach(){

        findMyTeamProfileDTO = new FindMyTeamProfileDTO()
                .userSeq(1L)
                .teamSeq(3L);

        modifyMyTeamProfileDTO = new ModifyMyTeamProfileDTO()
                .findMyTeamProfileDTO(findMyTeamProfileDTO)
                .backNumber("11");

        memberDTO = new MemberDTO()
                .userSeq(1L)
                .teamSeq(3L)
                .userName("Dong")
                .teamName("JIS");

        session = new MockHttpSession();
    }
    @Test
    void findProfile(){
        //given
        when(myTeamRepository.findProfileByUserSeqAndTeamSeq(findMyTeamProfileDTO)).thenReturn(memberDTO);
        //when
        MemberDTO profile = teamMemberService.findProfile(findMyTeamProfileDTO);
        //then
        assertThat(profile).isEqualTo(memberDTO);
    }
    @Test
    void updateProfile(){
        //given
        when(teamMemberRepository.updateMyTeamProfile(modifyMyTeamProfileDTO)).thenReturn(1);
        //when
        int ret = teamMemberService.updateMyTeamProfile(modifyMyTeamProfileDTO);
        //then
        assertThat(ret).isEqualTo(1);
    }

    @Test
    void deleteProfile(){
        //given
        doNothing().when(teamMemberRepository).deleteMyTeamProfile(findMyTeamProfileDTO);
        //when
        teamMemberService.deleteMyTeamProfile(findMyTeamProfileDTO);
        //then
        verify(teamMemberRepository).deleteMyTeamProfile(findMyTeamProfileDTO);
    }
}