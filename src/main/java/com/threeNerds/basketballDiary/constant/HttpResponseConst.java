package com.threeNerds.basketballDiary.constant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

// TODO 패키지 변경 : 현재 >> constant
public class HttpResponseConst {

    /** 사용법
     * 1. Controller에 HttpResponseConst 클래스를 static으로 import 시켜주기.
     *      - 클래스명 작성하지 않고도 필드를 호출할 수 있게하기 위함.
     * 2. 서비스의 결과에 따라서 HTTP표준응답을 return해주기
     *      - 해당 클래스의 필드를 사용하게 되면 클라이언트로 Http Body값 설정을 하지 못함.
     *      - 따라서 body값(즉, 조회된 데이터)을 클라이언트로 보내주기 위해서는 ResponseEntity.ok().body()메소드안에 조회된 데이터를 파라미터로 넣어줘야 함.
     *      - 혹은 서비스 동작 결과를 상태코드와 별개로 넣어주고 싶다면 해당 클래스 필드외에 ResponseEntity의 static 메소드를 사용해야함!
     */
    // 200대 응답코드
    public static final ResponseEntity RESPONSE_OK = new ResponseEntity(HttpStatus.OK);
    public static final ResponseEntity RESPONSE_CREATED = new ResponseEntity(HttpStatus.CREATED);
    public static final ResponseEntity RESPONSE_NO_CONTENT = new ResponseEntity(HttpStatus.NO_CONTENT);

    // 400대 응답코드
    public static final ResponseEntity RESPONSE_BAD_REQUEST = new ResponseEntity(HttpStatus.BAD_REQUEST);
    public static final ResponseEntity RESPONSE_UNAUTHORIZED = new ResponseEntity(HttpStatus.UNAUTHORIZED);
    public static final ResponseEntity RESPONSE_NOT_FOUND = new ResponseEntity(HttpStatus.NOT_FOUND);
    public static final ResponseEntity RESPONSE_CONFLICT = new ResponseEntity(HttpStatus.CONFLICT);
}

