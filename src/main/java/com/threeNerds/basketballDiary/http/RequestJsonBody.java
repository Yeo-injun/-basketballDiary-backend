package com.threeNerds.basketballDiary.http;

public class RequestJsonBody implements IRequestBody {

    public <T extends RequestJsonBody> T castSelfType( Object targetObj ) {
        return (T) targetObj;
    }
    
    // TODO Reflection으로 구현하기 혹은 인터페이스로 구현강제하기
    public RequestJsonBody toResponse( Object dto ) {
        
        return this;
    }
}
