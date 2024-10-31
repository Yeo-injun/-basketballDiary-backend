package com.threeNerds.basketballDiary.auth;


import com.threeNerds.basketballDiary.exception.error.ErrorMessageType;

import java.util.function.Supplier;

public interface AuthorizationStatus {
    boolean isPermission();

    ErrorMessageType getErrorMessage();

}
