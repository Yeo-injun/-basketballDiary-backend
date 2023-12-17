package com.threeNerds.basketballDiary.constant;

import org.springframework.http.ResponseEntity;


public class HttpResponseConst {

    /** 사용법
     * 1. Controller에 HttpResponseConst 클래스를 static으로 import 시켜주기.
     *      - 클래스명 작성하지 않고도 필드를 호출할 수 있게하기 위함.
     * 2. 서비스의 결과에 따라서 HTTP표준응답을 return해주기
     *      - 해당 클래스의 필드를 사용하게 되면 클라이언트로 Http Body값 설정을 하지 못함.
     *      - 따라서 body값(즉, 조회된 데이터)을 클라이언트로 보내주기 위해서는 ResponseEntity.ok().body()메소드안에 조회된 데이터를 파라미터로 넣어줘야 함.
     *      - 혹은 서비스 동작 결과를 상태코드와 별개로 넣어주고 싶다면 해당 클래스 필드외에 ResponseEntity의 static 메소드를 사용해야함!
     */
    //
    // 200 OK 시 Body가 비어있는 ResponseEntity객체를 static 메소드로 제공.
    // 위 패턴을 유지하면 객체 생성 비용( 성능과 메모리 사용 )에 유리할 것으로 판단됨.
    // 이미 static으로 생성된 동일 객체를 참조하기 때문.
    public static final ResponseEntity<Void> RESPONSE_OK = ResponseEntity.ok().build();
}

