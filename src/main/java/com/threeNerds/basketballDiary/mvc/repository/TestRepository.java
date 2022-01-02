package com.threeNerds.basketballDiary.mvc.repository;

import com.threeNerds.basketballDiary.mvc.dto.TestDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper // Mapper 인터페이스를 사용하면 별도의 java파일을 생성하지 않고 바로 xml로 접근가능!
public interface TestRepository {

    TestDTO testSelect();

}
