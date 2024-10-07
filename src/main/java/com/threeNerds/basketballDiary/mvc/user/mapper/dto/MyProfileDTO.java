package com.threeNerds.basketballDiary.mvc.user.mapper.dto;

import com.threeNerds.basketballDiary.constant.code.type.PositionCode;
import com.threeNerds.basketballDiary.mvc.user.domain.User;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MyProfileDTO {

    /* 시퀸스 */
    private Long userSeq;
    /* 아이디 */
    private String userId;
    /* 이름 */
    private String userName;
    /* 이메일 */
    private String email;
    /* 생년월일 */
    private String birthYmd;
    /* 성별 */
    private String gender;
    private String genderName;
    /* 키 */
    private Float height;
    /* 몸무게 */
    private Float weight;
    /* 포지션 코드 */
    private String positionCode;
    private String positionCodeName;
    /* 시도코드 */
    private String sidoCode;
    /* 시군구코드 */
    private String sigunguCode;
    /* 도로명 주소 */
    private String roadAddress;
    /* 사용자이미지경로 */
    private String userImagePath;

    public void setPositionCode( String positionCode ) {
        this.positionCode       = positionCode;
        this.positionCodeName   = PositionCode.nameOf( positionCode );
    }

    public void setGender( String gender ) {
        this.gender = gender;
        switch ( gender ) {
            case "01" : this.genderName = "남자"; break;
            case "02" : this.genderName = "여자"; break;
            default : this.genderName = "";
        }
    }
}
