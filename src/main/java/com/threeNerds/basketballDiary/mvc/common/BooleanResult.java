package com.threeNerds.basketballDiary.mvc.common;

import lombok.Getter;

@Getter
public class BooleanResult {
    private Boolean success;

    public BooleanResult(Boolean success) {
        this.success = success;
    }
}
