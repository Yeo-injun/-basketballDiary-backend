package com.threeNerds.basketballDiary.mvc.user.controller.response;

import com.threeNerds.basketballDiary.mvc.user.dto.UserDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class GetUsersExcludingTeamMembersResponse {

    private List<UserDTO> users;
    public GetUsersExcludingTeamMembersResponse( List<UserDTO> users ) {
        this.users = users;
    }
}
