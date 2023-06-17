package com.threeNerds.basketballDiary.exception;

import com.threeNerds.basketballDiary.exception.custom.MemberNotFound;
import com.threeNerds.basketballDiary.exception.custom.TeamNotFound;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Getter
public enum ErrorType {

    M001("M001","회원을 찾을 수 없습니다",MemberNotFound.class),

    T001("T001","팀을 찾을 수 없습니다.",TeamNotFound.class);

    private String errorCode;
    private String message;
    private Class<? extends BasketballException> exception;

    private final static Map<Class<? extends BasketballException>,ErrorType> codeMap = new HashMap<>();

    ErrorType(String errorCode, String message, Class<? extends BasketballException> exception) {
        this.errorCode = errorCode;
        this.message = message;
        this.exception = exception;
    }

    static {
        Arrays.stream(ErrorType.values())
                .forEach(ex -> codeMap.put(ex.getException(),ex));
    }

    public static ErrorType from(Class<? extends BasketballException> ex){
        return codeMap.get(ex);
    }
}
