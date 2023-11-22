package com.threeNerds.basketballDiary.exception.error;

import com.threeNerds.basketballDiary.file.exception.FileException;
import org.springframework.http.ResponseEntity;

public interface ErrorResponse {

    static ResponseEntity toEntity( ErrorMessageType errorType ) {
        // TODO 구현체에 해당 메소드가 static으로 구현되어 있지 않으면 null반환하는것인지?
        // 그렇다면 throw Exception을 던지자!
        throw new FileException();
    }
}
