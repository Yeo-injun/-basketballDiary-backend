package com.threeNerds.basketballDiary.auth.type;

public interface AuthType {

    default boolean isPermissionGranted( AuthLevel authLevel ) {
        return true;
    }

}
