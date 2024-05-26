package com.threeNerds.basketballDiary.mvc.user.controller.response;

import com.threeNerds.basketballDiary.mvc.user.dto.UserDTO;
import com.threeNerds.basketballDiary.mvc.user.service.dto.UserQuery;
import com.threeNerds.basketballDiary.pagination.Pagination;
import lombok.Getter;

import java.util.List;

@Getter
public class GetUsersExcludingTeamMembersResponse {

    private Pagination pagination;
    private List<UserDTO> users;
    public GetUsersExcludingTeamMembersResponse(    UserQuery.Result result ) {

        this.pagination = result.getPagination();
        this.users      = result.getUsers();
    }
}
