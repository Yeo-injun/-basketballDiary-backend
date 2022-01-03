package com.threeNerds.basketballDiary.mvc.controller;

import com.threeNerds.basketballDiary.mvc.dto.TestDTO;
import com.threeNerds.basketballDiary.mvc.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
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
        return "성주 바보바!!";
    }
}
