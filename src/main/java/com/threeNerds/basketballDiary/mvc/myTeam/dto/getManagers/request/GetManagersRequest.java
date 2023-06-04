package com.threeNerds.basketballDiary.mvc.myTeam.dto.getManagers.request;

import lombok.Getter;

@Getter
public class GetManagersRequest {
    private Long teamSeq;

    public GetManagersRequest(Long teamSeq) {
        this.teamSeq = teamSeq;
    }
}
