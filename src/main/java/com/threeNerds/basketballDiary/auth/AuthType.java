package com.threeNerds.basketballDiary.auth;


public interface AuthType {

    default boolean isPermissionGranted( AuthType userAuth ) {
        return true;
    }

    String getName();
    String getLevel();
}
