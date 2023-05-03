package com.threeNerds.basketballDiary.http;

public class ResponseJsonBody implements IResponseBody {

    public <T> T cast( Object targetObj ) {
        return (T) targetObj;
    }
}
