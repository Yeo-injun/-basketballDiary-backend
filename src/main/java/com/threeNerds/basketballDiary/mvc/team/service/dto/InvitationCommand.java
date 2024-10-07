package com.threeNerds.basketballDiary.mvc.team.service.dto;

import com.threeNerds.basketballDiary.constant.code.type.JoinRequestStateCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvitationCommand {
    private Long teamSeq;
    private Long userSeq;
    private JoinRequestStateCode joinRequestState;
}
