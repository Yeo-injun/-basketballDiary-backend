package com.threeNerds.basketballDiary.auth.validation.type;


public interface AuthType {

    default boolean isPermissionGranted( AuthType userAuth ) {
        return true;
    }

    String getName();
    String getLevel();
}
