package com.threeNerds.basketballDiary.session;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter @Setter
public class SessionDTO {

    private Long userSeq;

    private String userId;

    private Map<Long,Long> userAuth = new HashMap<>();

    public SessionDTO(Long userSeq, String userId) {
        this.userSeq = userSeq;
        this.userId = userId;
    }
}
