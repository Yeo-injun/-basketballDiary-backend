package com.threeNerds.basketballDiary.mvc.user.dto;

import com.threeNerds.basketballDiary.pagination.Pagination;
import lombok.Getter;

@Getter
public class UserQueryCondDTO {

    /** 페이징 객체 */
    private Pagination pagination;

    /** 팀Seq **/
    private Long teamSeq;
    /** 사용자명 **/
    private String userName;
    /** 이메일 **/
    private String email;

    public UserQueryCondDTO pagination( Pagination pagination ) {
        this.pagination = pagination;
        return this;
    }

    public UserQueryCondDTO teamSeq( Long teamSeq ) {
        this.teamSeq = teamSeq;
        return this;
    }

    public UserQueryCondDTO userName( String userName ) {
        this.userName = userName;
        return this;
    }

    public UserQueryCondDTO email( String email ) {
        this.email = email;
        return this;
    }
}
