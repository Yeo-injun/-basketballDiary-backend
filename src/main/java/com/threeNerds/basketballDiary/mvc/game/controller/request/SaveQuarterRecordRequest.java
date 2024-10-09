package com.threeNerds.basketballDiary.mvc.game.controller.request;

import com.threeNerds.basketballDiary.mvc.game.mapper.dto.SavePlayerRecordDTO;
import com.threeNerds.basketballDiary.mvc.game.service.dto.QuarterRecordCommand;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import java.util.List;


@Getter
public class SaveQuarterRecordRequest {

    private Long gameSeq;
    private String quarterCode;

    @NotEmpty(message = "쿼터 시간은 필수 입력항목입니다.")
    private String quarterTime;
//    @NotEmpty(message = "홈팀 선수들의 경기기록 정보가 없습니다.") // TODO 향후 홈/어웨이 팀만 저장할 수 있도록 처리 검토
    private List<SavePlayerRecordDTO> homeTeamPlayerRecords;
//    @NotEmpty(message = "어웨이팀 선수들의 경기기록 정보가 없습니다.") // TODO 향후 홈/어웨이 팀만 저장할 수 있도록 처리 검토
    private List<SavePlayerRecordDTO> awayTeamPlayerRecords;

    public QuarterRecordCommand toCommand( Long gameSeq, String quarterCode ) {
        return QuarterRecordCommand.builder()
                .gameSeq(       gameSeq )
                .quarterCode(   quarterCode )
                .quarterTime(   this.quarterTime )
                .homeTeamPlayerRecords( this.homeTeamPlayerRecords )
                .awayTeamPlayerRecords( this.awayTeamPlayerRecords )
                .build();
    }
}
