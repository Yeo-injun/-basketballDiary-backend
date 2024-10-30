package com.threeNerds.basketballDiary.auth.type;

public interface AuthType < T > {

    default boolean isPermissionGranted( T authLevel ) {
        return true;
    }

}
