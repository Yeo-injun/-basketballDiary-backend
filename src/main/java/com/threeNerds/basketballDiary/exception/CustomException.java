package com.threeNerds.basketballDiary.exception;

import com.threeNerds.basketballDiary.exception.error.ErrorMessageType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {

    /**
     * Exception을 throw할때 ErrorMessageType을 받는다.
     * 받은 MessageType을 활용해 ErrorMessage를 핸들링한다.
     */
    private final ErrorMessageType error;
}
