package com.threeNerds.basketballDiary.mvc.user.service.dto;

import com.threeNerds.basketballDiary.mvc.user.dto.UserDTO;
import com.threeNerds.basketballDiary.pagination.Pagination;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserQuery {

    // 페이징 처리를 위한 조회 페이지 번호
    private Integer pageNo;

    @NotNull
    private Long teamSeq;
    private String userName;
    private String email;


    @Getter
    public class Result {
        private final Pagination pagination;
        private final List<UserDTO> users;
        Result( Pagination pagination, List<UserDTO> users ) {
            this.pagination = pagination;
            this.users      = users;
        }
    }

    public Result buildResult( Pagination pagination, List<UserDTO> users ) {
        return new Result( pagination, users );
    }
}
