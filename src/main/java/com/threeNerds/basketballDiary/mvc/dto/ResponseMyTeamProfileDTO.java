package com.threeNerds.basketballDiary.mvc.dto;

import com.threeNerds.basketballDiary.mvc.controller.UserController;
import lombok.Getter;

@Getter
public class ResponseMyTeamProfileDTO {

    /** 회원 이름 **/
    private String userName;
    /** 팀 명칭 **/
    private String teamName;
    /** 등 번호 **/
    private String backNumber;

    public ResponseMyTeamProfileDTO responseUserName(String userName){
        this.userName=userName;
        return this;
    }
    public ResponseMyTeamProfileDTO responseTeamName(String teamName){
        this.teamName=teamName;
        return this;
    }
    public ResponseMyTeamProfileDTO responseBackNumber(String backNumber){
        this.backNumber=backNumber;
        return this;
    }
}
