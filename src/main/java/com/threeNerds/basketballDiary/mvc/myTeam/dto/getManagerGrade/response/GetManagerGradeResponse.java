package com.threeNerds.basketballDiary.mvc.myTeam.dto.getManagerGrade.response;

import com.threeNerds.basketballDiary.mvc.myTeam.dto.MemberDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class GetManagerGradeResponse {

    List<MemberDTO> memberList;

    public GetManagerGradeResponse(List<MemberDTO> memberList) {
        this.memberList = memberList;
    }
}
