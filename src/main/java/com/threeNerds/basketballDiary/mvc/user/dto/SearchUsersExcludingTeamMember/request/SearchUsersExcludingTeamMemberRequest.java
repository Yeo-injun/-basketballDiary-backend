package com.threeNerds.basketballDiary.mvc.user.dto.SearchUsersExcludingTeamMember.request;

import com.threeNerds.basketballDiary.http.RequestJsonBody;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
public class SearchUsersExcludingTeamMemberRequest extends RequestJsonBody {

    @NotNull
    private Long teamSeq;
    private String userName;
    private String email;

    public SearchUsersExcludingTeamMemberRequest( Long teamSeq, String userName, String email ) {
        this.teamSeq = teamSeq;
        this.userName = userName;
        this.email = email;
    }
}
