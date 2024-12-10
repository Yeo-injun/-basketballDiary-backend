package com.threeNerds.basketballDiary.file.exception;

import lombok.Getter;

@Getter
public class TransferFileException extends FileException {

    private final String message;

    public TransferFileException( String message ) {
        this.message = message;
    }
}
