package com.threeNerds.basketballDiary.mvc.dto.user.user;

import com.threeNerds.basketballDiary.mvc.domain.User;
import lombok.Getter;

@Getter
public class LoginUserDTO {

    private String userId;
    private String password;

    public LoginUserDTO userId(String userId) {
        this.userId = userId;
        return this;
    }
    public LoginUserDTO password(String password) {
        this.password = password;
        return this;
    }

    // TODO  테스트코드에서만 사용하는 것으로 파악됨.
    // DTO는 데이터 전달용 객체임.
    // 따라서 도메인객체와 달리 도메인로직을 가지고 있지 않기 때문에 메소드는 getter와 setter만
    static public LoginUserDTO createLoginUserDTO(User user){
        return new LoginUserDTO()
                .userId(user.getUserId())
                .password(user.getPassword());
    }
}
