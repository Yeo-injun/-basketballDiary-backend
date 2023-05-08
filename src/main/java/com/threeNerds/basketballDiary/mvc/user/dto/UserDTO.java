package com.threeNerds.basketballDiary.mvc.user.dto;

import com.threeNerds.basketballDiary.constant.code.PositionCode;
import com.threeNerds.basketballDiary.mvc.user.domain.User;
import lombok.Getter;

import javax.swing.text.Position;
import java.time.LocalDate;

@Getter
public class UserDTO {

    /* 시퀸스 */
    private Long userSeq;
    /* 아이디 */
    private String userId;
    /* 패스워드 */
    private String password;
    /* 이름 */
    private String userName;
    /* 포지션 코드 */
    private String positionCode;
    private String positionCodeName;
    /* 이메일 */
    private String email;
    /* 성별 */
    private String gender;
    private String genderName;
    /* 생년월일 */
    private String birthYmd;
    /* 키 */
    private Double height;
    /* 몸무게 */
    private Double weight;
    /* 등록일자 */
    private LocalDate regDate;
    /* 업데이트 일자 */
    private LocalDate updateDate;
    /* 회원/비회원 */
    private String userRegYn;
    /* 시도코드 */
    private String sidoCode;
    /* 시군구코드 */
    private String sigunguCode;
    /* 도로명 주소*/
    private String roadAddress;

    public void setPositionCode( String positionCode ) {
        this.positionCode = positionCode;
        this.positionCodeName = PositionCode.nameOf( positionCode );
    }

    public void setGender( String gender ) {
        this.gender = gender;
        switch ( gender ) {
            case "01" : this.genderName = "남자"; break;
            case "02" : this.genderName = "여자"; break;
            default : this.genderName = "";
        }
    }

    public UserDTO userSeq(Long userSeq){
        this.userSeq = userSeq;
        return this;
    }
    public UserDTO userId(String userId){
        this.userId = userId;
        return this;
    }
    public UserDTO password(String password){
        this.password = password;
        return this;
    }
    public UserDTO userName(String userName){
        this.userName = userName;
        return this;
    }
    public UserDTO positionCode(String positionCode){
        this.positionCode = positionCode;
        return this;
    }
    public UserDTO email(String email){
        this.email = email;
        return this;
    }
    public UserDTO gender(String gender){
        this.gender = gender;
        return this;
    }
    public UserDTO birthYmd(String birthYmd){
        this.birthYmd = birthYmd;
        return this;
    }
    public UserDTO height(Double height){
        this.height = height;
        return this;
    }
    public UserDTO weight(Double weight){
        this.weight = weight;
        return this;
    }
    public UserDTO regDate(LocalDate regDate){
        this.regDate = regDate;
        return this;
    }
    public UserDTO updateDate(LocalDate updateDate){
        this.updateDate = updateDate;
        return this;
    }
    public UserDTO userRegYn(String userRegYn){
        this.userRegYn = userRegYn;
        return this;
    }
    public UserDTO sidoCode(String sidoCode){
        this.sidoCode = sidoCode;
        return this;
    }
    public UserDTO sigunguCode(String sigunguCode){
        this.sigunguCode = sigunguCode;
        return this;
    }
    public UserDTO roadAddress(String roadAddress){
        this.roadAddress = roadAddress;
        return this;
    }

    public static UserDTO getInstance(User user) {
        return new UserDTO().userId(user.getUserId())
                .password(user.getPassword())
                .userName(user.getName())
                .positionCode(user.getPositionCode())
                .email(user.getEmail())
                .gender(user.getGender())
                .birthYmd(user.getBirthYmd())
                .height(user.getHeight())
                .weight(user.getWeight())
                .regDate(user.getRegDate())
                .updateDate(user.getUpdateDate())
                .userRegYn(user.getUserRegYn())
                .sidoCode(user.getSidoCode())
                .sigunguCode(user.getSigunguCode())
                .roadAddress(user.getRoadAddress());
    }
}
