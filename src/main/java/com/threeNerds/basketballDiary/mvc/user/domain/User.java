package com.threeNerds.basketballDiary.mvc.user.domain;

import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.error.DomainErrorType;
import com.threeNerds.basketballDiary.mvc.auth.controller.request.CreateUserRequest;
import com.threeNerds.basketballDiary.mvc.authUser.service.dto.ProfileCommand;
import com.threeNerds.basketballDiary.utils.EncryptUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Slf4j
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    /* PK : id */
    private Long userSeq;
    /* 아이디 */
    private String userId;
    /* 패스워드 */
    private String password;
    /* 이름 */
    private String name;
    /* 포지션 코드 */
    private String positionCode;
    /* 이메일 */
    private String email;
    /* 성별 */
    private String gender;
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
    /* 도로명주소 */
    private String roadAddress;

    public static User createForRegistration( CreateUserRequest userInfo ){
        /** 비밀번호 암호화 */
        String cryptPassword = EncryptUtil.getEncrypt(userInfo.getPassword(), userInfo.getUserId());

        return User.builder()
                .userId(userInfo.getUserId())
                .password(cryptPassword)
                .name(userInfo.getName())
                .email(userInfo.getEmail())
                .gender(userInfo.getGender())
                .birthYmd(userInfo.getBirthYmd())
                .height(userInfo.getHeight())
                .weight(userInfo.getWeight())
                .regDate(LocalDate.now())
                .updateDate(LocalDate.now())
                .userRegYn("Y")
                .sidoCode(userInfo.getSidoCode())
                .sigunguCode(userInfo.getSigunguCode())
                .positionCode(userInfo.getPositionCode())
                .roadAddress(userInfo.getRoadAddress())
                .build();
    }

    public static User ofUpdate( ProfileCommand command ) {
        return User.builder()
                .userSeq(       command.getUserSeq() )
                .name(          command.getUserName() )
                .email(         command.getEmail() )
                .gender(        command.getGender() )
                .birthYmd(      command.getBirthYmd() )
                .height(        command.getHeight() )
                .weight(        command.getWeight() )
                .updateDate(    LocalDate.now() )
                .sidoCode(      command.getSidoCode() )
                .sigunguCode(   command.getSigunguCode() )
                .positionCode(  command.getPositionCode() )
                .roadAddress(   command.getRoadAddress() )
                .build();
    }
    public boolean checkAuthentication( String userId, String plainPassword ) {
        /** 평문비밀번호 null체크 */
        if (plainPassword == null || plainPassword.isEmpty()) {
            throw new CustomException( DomainErrorType.NO_EXIST_PASSWORD );
        }

        /** 비밀번호 암호화 : 평문비밀번호 + userId로 */
        String cryptPassword = EncryptUtil.getEncrypt(plainPassword, userId);
        log.info("cryptoPassword = {}", cryptPassword);

        return this.password.equals( cryptPassword );
    }

}
