package com.threeNerds.basketballDiary.http;

public class RequestJsonBody implements IRequestBody {

    public <T extends RequestJsonBody> T castSelfType( Object targetObj ) {
        return (T) targetObj;
    }
}
