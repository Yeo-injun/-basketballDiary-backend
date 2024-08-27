package com.threeNerds.basketballDiary.mvc.myTeam.dto;

import com.threeNerds.basketballDiary.constant.code.type.JoinRequestStateCode;
import com.threeNerds.basketballDiary.constant.code.type.JoinRequestTypeCode;
import com.threeNerds.basketballDiary.constant.code.type.PositionCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
// TODO 해당 클래스명 변경 MyTeamJoinRequestDTO로...
public class InvitationDTO {
    private Long teamSeq;
    // 가입요청유형상태코드
    private String joinRequestTypeCode;
    private String joinRequestTypeCodeName;
    // 가입요청상태코드
    private String joinRequestStateCode;
    private String joinRequestStateCodeName;

    // 팀가입요청SEQ
    private Long teamJoinRequestSeq;

    // 가입요청일시
    private LocalDate requestDate;

    // 확정일시
    private LocalDate confirmationDate;

    // 이름
    private String name;

    // 이메일
    private String email;

    // 포지션코드
    private String positionCode;
    private String positionCodeName;

    // 키
    private Float height;

    // 몸무게
    private Float weight;


    InvitationDTO( Long teamSeq, JoinRequestStateCode state ) {
        this.teamSeq                = teamSeq;
        this.joinRequestTypeCode    = JoinRequestTypeCode.INVITATION.getCode();
        this.joinRequestStateCode   = state == null ? null : state.getCode();
    }
    public static InvitationDTO of( Long teamSeq, JoinRequestStateCode state ) {
        return new InvitationDTO( teamSeq, state );
    }

    // 쿼리 조회시 코드명 세팅을 위해 setter정의
    public void setJoinRequestTypeCode( String code ) {
        this.joinRequestTypeCode = code;
        this.joinRequestTypeCodeName = JoinRequestTypeCode.nameOf( code );
    }
    public void setJoinRequestStateCode( String code ) {
        this.joinRequestStateCode = code;
        this.joinRequestStateCodeName = JoinRequestStateCode.nameOf( code );
    }
    public void setPositionCode( String code ) {
        this.positionCode = code;
        this.positionCodeName = PositionCode.nameOf( code );
    }
}
