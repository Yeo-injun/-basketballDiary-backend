package com.threeNerds.basketballDiary.utils;

import com.threeNerds.basketballDiary.constant.code.QuarterCode;
import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.Error;
import org.springframework.util.ObjectUtils;

public class ValidateUtil {

    public static boolean check(Object[] targetValArr)
    {
        // TODO 에러메세지 수정 - 유효성 체크를 위한 목록이 없음
        if (targetValArr.length == 0) {
            throw new CustomException(Error.INVALID_PARAMETER);
        }

        for (Object targetVal : targetValArr) {
            check(targetVal);
        }

        return true;
    }

    public static boolean check(Object targetVal)
    {
        if (targetVal == null) {
            throw new CustomException(Error.NO_PARAMETER);
        }

        if (targetVal instanceof String) {
            boolean isBlank = ((String) targetVal).isBlank();
            if (isBlank) {
                throw new CustomException(Error.NO_PARAMETER);
            }
        }

        if (ObjectUtils.isEmpty(targetVal)) {
            throw new CustomException(Error.NO_PARAMETER);
        }

        return true;
    }

}
