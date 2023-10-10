package com.threeNerds.basketballDiary.mvc.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.threeNerds.basketballDiary.mvc.auth.controller.request.CheckDuplicateUserIdRequest;
import com.threeNerds.basketballDiary.mvc.auth.controller.request.CreateUserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class AuthApiTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    private static final String URL = "/api/user";

    @Test
    void 회원가입_테스트() throws Exception {
        //given
        var request = new CreateUserRequest()
                .userId("testId")
                .password("1234")
                .name("jipdol2")
                .email("jipdol2@gmail.com")
                .gender("01")
                .height(183.1);
        String json = objectMapper.writeValueAsString(request);
        //expected
        mockMvc.perform(post(URL+"/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andDo(print());

            //아이디 중복 체크 시도
        var requestId = new CheckDuplicateUserIdRequest()
                .userId("testId");

        json = objectMapper.writeValueAsString(requestId);
        mockMvc.perform(post(URL+"/duplicationCheck")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andDo(print());
    }
}
