package com.threeNerds.basketballDiary.mvc.user.domain;

import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.Error;
import com.threeNerds.basketballDiary.mvc.auth.dto.CreateUserDTO;
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
    private String userName; // TODO name으로 통일 - DB컬럼명과 일치해야 함.
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

    public static User createForRegistration(CreateUserDTO userDTO)
    {
        LocalDate today = LocalDate.now();
        /** 비밀번호 암호화 */
        String plainPassword = userDTO.getPassword();
        String userId        = userDTO.getUserId();
        String cryptPassword = EncryptUtil.getEncrypt(plainPassword, userId);
        return User.builder()
                .userId(userId)
                .password(cryptPassword)
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .gender(userDTO.getGender())
                .birthYmd(userDTO.getBirthYmd())
                .height(userDTO.getHeight())
                .weight(userDTO.getWeight())
                .regDate(today)
                .updateDate(today)
                .userRegYn("Y")
                .sidoCode(userDTO.getSidoCode())
                .sigunguCode(userDTO.getSigunguCode())
                .positionCode(userDTO.getPositionCode())
                .roadAddress(userDTO.getRoadAddress())
                .build();
    }

    public boolean isAuthUser (String userId, String plainPassword)
    {
        /** 평문비밀번호 null체크 */
        if (plainPassword == null || plainPassword.isEmpty()) {
            throw new CustomException(Error.NO_EXIST_PASSWORD);
        }

        /** 비밀번호 암호화 : 평문비밀번호 + userId로 */
        String cryptPassword = EncryptUtil.getEncrypt(plainPassword, userId);
        log.info("cryptoPassword = {}", cryptPassword);

        boolean isNotCorrectPassword = !this.password.equals(cryptPassword);
        if (isNotCorrectPassword) {
            throw new CustomException(Error.INCORRECT_PASSWORD);
        }
        return true;
    }

}
