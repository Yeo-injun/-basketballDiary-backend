package com.threeNerds.basketballDiary.mvc.myTeam.service.dto;

import com.threeNerds.basketballDiary.constant.code.type.JoinRequestStateCode;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.TeamJoinRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JoinRequestQuery {
    private Long userSeq;
    private Long teamSeq;
    private JoinRequestStateCode joinRequestState;

    @Getter
    public class Result {
        List<TeamJoinRequestDTO> joinRequests;

        Result(List<TeamJoinRequestDTO> joinRequests ) {
            this.joinRequests = joinRequests;
        }
    }

    public Result buildResult( List<TeamJoinRequestDTO> joinRequests ) {
        return new Result( joinRequests );
    }


}
