package com.threeNerds.basketballDiary.mvc.service;

import com.threeNerds.basketballDiary.mvc.controller.UserController;
import com.threeNerds.basketballDiary.mvc.repository.TeamMemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@Transactional
class TeamMemberServiceTest {

    MockHttpSession session;

    @InjectMocks
    private TeamMemberService teamMemberService;

    @Mock
    private TeamMemberRepository teamMemberRepository;

    @BeforeEach
    void setUpEach(){
        UserController.FindMyTeamProfileDTO findMyTeamProfileDTO = new UserController.FindMyTeamProfileDTO()
                .userSeq(1L)
                .teamSeq(3L);

        session = new MockHttpSession();
    }
    @Test
    void findProfile(){

    }
    @Test
    void updateProfile(){

    }

}