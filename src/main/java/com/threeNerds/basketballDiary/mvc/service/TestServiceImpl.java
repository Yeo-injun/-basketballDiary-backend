package com.threeNerds.basketballDiary.mvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    TestRepository testMapper;

    @Override
    public TestDTO testSQL() {
        TestDTO testResult = testMapper.testSelect();
        return testResult;
    }
}
