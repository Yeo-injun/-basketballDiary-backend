package com.threeNerds.basketballDiary.mvc.user.dto.SearchUsersExcludingTeamMember.response;

import com.threeNerds.basketballDiary.http.ResponseJsonBody;
import com.threeNerds.basketballDiary.mvc.user.dto.UserDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class SearchUsersExcludingTeamMemberResponse extends ResponseJsonBody {

    private List<UserDTO> users;

    public SearchUsersExcludingTeamMemberResponse ( List<UserDTO> users ) {
        this.users = users;
    }
}
