package com.threeNerds.basketballDiary.mvc.game.dto;

import com.threeNerds.basketballDiary.mvc.game.controller.dto.QuarterEntryDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class QuarterEntryInfoDTO {

    private Long gameSeq;
    private String quarterCode;
    private String homeAwayCode;
    private List<QuarterEntryDTO> quarterEntryList;

    public QuarterEntryInfoDTO gameSeq(Long gameSeq) {
        this.gameSeq = gameSeq;
        return this;
    }

    public QuarterEntryInfoDTO quarterCode(String quarterCode) {
        this.quarterCode = quarterCode;
        return this;
    }

    public QuarterEntryInfoDTO homeAwayCode(String homeAwayCode) {
        this.homeAwayCode = homeAwayCode;
        return this;
    }

    public QuarterEntryInfoDTO quarterEntryList(List<QuarterEntryDTO> quarterEntryList) {
        this.quarterEntryList = quarterEntryList;
        return this;
    }
}
