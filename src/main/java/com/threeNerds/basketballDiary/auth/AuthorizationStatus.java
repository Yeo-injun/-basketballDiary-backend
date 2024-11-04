package com.threeNerds.basketballDiary.auth;

import com.threeNerds.basketballDiary.auth.exception.AuthCheckException;
import com.threeNerds.basketballDiary.auth.exception.AuthorizationException;

public interface AuthorizationStatus {
    boolean isPermission();
    AuthorizationException getException();

}
