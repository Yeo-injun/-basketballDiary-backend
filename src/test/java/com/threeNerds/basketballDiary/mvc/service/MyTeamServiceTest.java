package com.threeNerds.basketballDiary.mvc.service;

import com.threeNerds.basketballDiary.constant.code.PositionCode;
import com.threeNerds.basketballDiary.constant.code.TeamAuthCode;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.MemberDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.service.MyTeamService;
import com.threeNerds.basketballDiary.mvc.myTeam.repository.MyTeamRepository;
import com.threeNerds.basketballDiary.mvc.repository.TeamRegularExerciseRepository;
import com.threeNerds.basketballDiary.mvc.repository.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpSession;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MyTeamServiceTest {

    MockHttpSession session;

    @InjectMocks
    MyTeamService myTeamService;
    @Mock
    MyTeamRepository myTeamRepository;
    @Mock
    TeamRepository teamRepository;
    @Mock
    TeamRegularExerciseRepository teamRegularExerciseRepository;

    MemberDTO memberDTO;

    @BeforeEach
    void setUp(){
        memberDTO = new MemberDTO()
                .teamMemberSeq(3L)
                .userSeq(3L)
                .teamSeq(3L)
                .teamAuthCode(TeamAuthCode.MANAGER.getCode())
                .positionCode(PositionCode.SMALL_FORWARD.getCode())
                .userName("Kim")
                .birthYmd("2022-01-18")
                .height("176.5")
                .weight("74")
                .backNumber("23")
                .joinYmd("20220224");
    }

    @Test
    @DisplayName("소속팀 운영진 목록 조회 : 존재O")
    void findManagers_notEmpty(){
        //given
        when(myTeamService.findManagers(3L)).thenReturn(List.of(memberDTO));
        //when
        List<MemberDTO> managers = myTeamService.findManagers(3L);
        //then
        assertThat(managers).isNotEmpty();
    }

    @Test
    @DisplayName("소속팀 운영진 목록 조회 : 존재X")
    void findManagers_empty(){
        //given
        when(myTeamService.findManagers(3L)).thenReturn(Collections.emptyList());
        //when
        List<MemberDTO> managers = myTeamService.findManagers(3L);
        //then
        assertThat(managers).isEmpty();
    }

    @Test
    @DisplayName("소속팀 팀원 목록 조회")
    void findMembers(){
        //given
        //when
        //then
    }

    @Test
    @DisplayName("소속팀 목록 조회")
    void findTeams(){
        //given
        //when
        //then
    }

    @Test
    @DisplayName("소속팀 단건 조회")
    void findTeam(){
        //given
        //when
        //then
    }

    @Test
    @DisplayName("소속팀 수정")
    void modifyMyTeam(){
        //given
        //when
        //then
    }

    @Test
    @DisplayName("소속팀 삭제(팀 삭제와 동일)")
    void deleteMyTeam(){
        //given
        //when
        //then
    }
}