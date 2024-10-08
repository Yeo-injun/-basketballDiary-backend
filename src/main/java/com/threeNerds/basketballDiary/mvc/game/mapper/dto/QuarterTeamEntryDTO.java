package com.threeNerds.basketballDiary.mvc.game.mapper.dto;

import com.threeNerds.basketballDiary.constant.code.type.HomeAwayCode;
import lombok.Getter;

import java.util.List;

@Getter
public class QuarterTeamEntryDTO {

    private String teamName;
    private String homeAwayCode;
    private String homeAwayCodeName;
    private List<QuarterPlayerRecordDTO> entry;

    public QuarterTeamEntryDTO teamName( String teamName ) {
        this.teamName = teamName;
        return this;
    }

    public QuarterTeamEntryDTO homeAwayCode( String homeAwayCode ) {
        this.homeAwayCode = homeAwayCode;
        this.homeAwayCodeName = HomeAwayCode.nameOf( homeAwayCode );
        return this;
    }

    public QuarterTeamEntryDTO entry( List<QuarterPlayerRecordDTO> entry ) {
        this.entry = entry;
        return this;
    }
}
