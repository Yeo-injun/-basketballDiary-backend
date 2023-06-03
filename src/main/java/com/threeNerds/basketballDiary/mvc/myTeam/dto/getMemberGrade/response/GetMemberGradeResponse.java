package com.threeNerds.basketballDiary.mvc.myTeam.dto.getMemberGrade.response;

import com.threeNerds.basketballDiary.mvc.myTeam.dto.MemberDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class GetMemberGradeResponse {

    List<MemberDTO> memberList;

    public GetMemberGradeResponse(List<MemberDTO> memberList) {
        this.memberList = memberList;
    }
}