package com.threeNerds.basketballDiary.mvc.service;

import com.threeNerds.basketballDiary.constant.code.JoinRequestTypeCode;
import org.junit.jupiter.api.Test;


public class EnumCodeTest {
    @Test
    void 코드값으로_ENUM에서_코드이름추출하기() {
        String codeName = JoinRequestTypeCode.nameOf("01");
        System.out.println(codeName);
    }

    @Test
    void 코드값으로_ENUM에서_코드이름추출하기_code값이_없는_경우() {
        String codeName = JoinRequestTypeCode.nameOf("03");
        System.out.println(codeName);
    }
}
