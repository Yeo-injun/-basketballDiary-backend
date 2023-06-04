package com.threeNerds.basketballDiary.mvc.myTeam.dto.getManagers.response;

import com.threeNerds.basketballDiary.mvc.myTeam.dto.MemberDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class GetManagersResponse {

    List<MemberDTO> managers;

    public GetManagersResponse(List<MemberDTO> managers) {
        this.managers = managers;
    }
}
