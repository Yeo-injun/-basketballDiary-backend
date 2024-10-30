package com.threeNerds.basketballDiary.auth.type;

public interface AuthLevel {
    default String getInfo() {
        return "";
    }

}
