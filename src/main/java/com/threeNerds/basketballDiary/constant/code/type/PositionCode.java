package com.threeNerds.basketballDiary.constant.code.type;

import com.threeNerds.basketballDiary.constant.code.CodeType;
import com.threeNerds.basketballDiary.constant.code.CodeTypeUtil;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum PositionCode implements CodeType {

//    ALL("전체", "00"),
    GUARD("가드", "10"),
    POINT_GUARD("포인트가드", "11"),
    SHOOTING_GAURD("슈팅가드", "12"),
    FORWARD("포워드", "20"),
    SMALL_FORWARD("스몰포워드", "23"),
    POWER_FORWARD("파워포워드", "24"),
    CENTER("센터", "30"),
    TEMP_CODE("임시용코드", "40"), // TODO 임시 코드 차후 데이터 변경후 삭제 예정
    NULL_CODE("임시용코드", null); // TODO 임시 코드 차후 데이터 변경후 삭제 예정

    private final String name;
    private final String code;

    PositionCode(String name, String code) {
        this.name = name;
        this.code = code;
    }

    /**--------------------------------------
     * code값으로 code이름 가져오기
     *---------------------------------------*/
    public static String nameOf( String code ) {
        return CodeTypeUtil.getCodeName( values(), code );
    }

}
