package com.threeNerds.basketballDiary.mvc.service.impl;

import com.threeNerds.basketballDiary.mvc.dto.TestDTO;
import com.threeNerds.basketballDiary.mvc.repository.TestRepository;
import com.threeNerds.basketballDiary.mvc.service.TestService;
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
