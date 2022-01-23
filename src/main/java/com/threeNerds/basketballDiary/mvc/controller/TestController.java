package com.threeNerds.basketballDiary.mvc.controller;

import com.threeNerds.basketballDiary.mvc.dto.TestDTO;
import com.threeNerds.basketballDiary.mvc.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {

    @Autowired
    TestService testService;

    @GetMapping("/test")
    public TestDTO testOne() {
        TestDTO result = testService.testSQL();
        return result;
    }

    @GetMapping("/test2")
    public String testTwo() {
        TestDTO dto = new TestDTO();
        TestDTO result = returnTest(dto);

        log.debug("first referrence address {}", dto.getClass());
        log.debug("second referrence address {}", result.getClass());
        return "성주 바보바!!";
    }

    private TestDTO returnTest(TestDTO dto) {
        dto.setMemberId("하이루!");
        return dto;
    }
}
