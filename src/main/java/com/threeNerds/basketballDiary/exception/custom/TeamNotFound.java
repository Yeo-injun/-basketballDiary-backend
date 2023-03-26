package com.threeNerds.basketballDiary.exception.custom;

public class TeamNotFound extends BasketballException{

    private int code = 404;

    private static final String message = "팀이 존재하지 않습니다";

    public TeamNotFound() {
        super(message);
    }

    @Override
    public int getStatusCode() {
        return code;
    }
}
