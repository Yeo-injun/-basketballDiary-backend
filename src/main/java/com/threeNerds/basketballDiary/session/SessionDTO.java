package com.threeNerds.basketballDiary.session;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter @Setter
public class SessionDTO {
    // SessionDTO가 아닌 MemberDTO로 이름을 바꾸는 것이 어떤지.
    // SessionDTO가 가지고 있는 데이터는 로그인한 회원 즉, Member에 대한 데이터이고,
    // Member에 대한 데이터를 Session에 저장해두는 것이니까 굳이 Session을 클래스 이름으로 사용할 필요는 없는듯
    // 해당 클래스의 역할과 책임에 따라 작명하는 것이 더 명확할 듯 - LoginInterceptor에서도 SessionDTO 인스턴스의 변수명을 memberDTO로 사용해서 생각해봄
    private Long userSeq;

    private String userId;

    private Map<Long,Long> userAuth = new HashMap<>();

    public SessionDTO(Map<Long, Long> userAuth) {
        this.userAuth = userAuth;
    }

    public SessionDTO(Long userSeq, String userId) {
        this.userSeq = userSeq;
        this.userId = userId;
    }

    public SessionDTO(Long userSeq, String userId, Map<Long, Long> userAuth) {
        this.userSeq = userSeq;
        this.userId = userId;
        this.userAuth = userAuth;
    }
}
