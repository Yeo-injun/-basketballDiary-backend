package com.threeNerds.basketballDiary.mvc.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.threeNerds.basketballDiary.mvc.user.controller.request.SignUpRequest;
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
    void 회원가입_테스트_valid() throws Exception{
//        //given
//        var request = new SignUpRequest()
//                .userId("testId")
//                .password("1234")
//                .name("jipdol2")
//                .email("jipdol2@gmail.com")
//                .gender("01");
//        //expected
//        String json = objectMapper.writeValueAsString(request);
//
//        mockMvc.perform(post(URL+"/registration")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.status").value( 400 ) )
//                .andExpect(jsonPath("$.message").value( "API메세지 규격 오류입니다." ) )
//                .andExpect(jsonPath("$.validations[0].name").value( "height" ) )
//                .andExpect(jsonPath("$.validations[0].message").value( "height 는 필수입니다." ) )
//                .andDo(print());
    }

    @Test
    void 회원가입_테스트() throws Exception {
        //given
//        var request = new CreateUserRequest()
//                .userId("testId")
//                .password("1234")
//                .name("jipdol2")
//                .email("jipdol2@gmail.com")
//                .gender("01")
//                .height(183.1);
//        String json = objectMapper.writeValueAsString(request);
//        //expected
//        mockMvc.perform(post(URL+"/registration")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(json))
//                .andExpect(status().isOk())
//                .andDo(print());
//
//            //아이디 중복 체크 시도
//        var requestId = new CheckDuplicateUserIdRequest()
//                .userId("testId");
//
//        json = objectMapper.writeValueAsString(requestId);
//        mockMvc.perform(post(URL+"/duplicationCheck")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.isDuplicated").value(true))
//                .andDo(print());
    }
}
