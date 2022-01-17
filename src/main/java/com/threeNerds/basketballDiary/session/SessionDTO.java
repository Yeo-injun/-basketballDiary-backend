package com.threeNerds.basketballDiary.session;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SessionDTO {

    private Long userSeq;

    private String userId;

    public SessionDTO(Long userSeq, String userId) {
        this.userSeq = userSeq;
        this.userId = userId;
    }
}
