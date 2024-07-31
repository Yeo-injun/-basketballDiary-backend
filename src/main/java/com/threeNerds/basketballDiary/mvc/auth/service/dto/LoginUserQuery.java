
package com.threeNerds.basketballDiary.mvc.auth.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserQuery {

    private String userId;
    private String password;

    @Getter
    public class Result {
        private final String userId;
        private final Long userSeq;

        Result( String userId, Long userSeq ) {
            this.userId  = userId;
            this.userSeq = userSeq;
        }
    }

    public LoginUserQuery.Result buildResult( Long userSeq ) {
        return new LoginUserQuery.Result( this.userId, userSeq );
    }
}
