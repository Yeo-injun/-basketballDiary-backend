package com.threeNerds.basketballDiary.mvc.game.dto;

import com.threeNerds.basketballDiary.mvc.game.controller.dto.QuarterEntryDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class QuarterEntryInfoDTO {

    private Long gameSeq;
    private String quarterCode; // TODO 삭제 예정
    private String homeAwayCode; // TODO 삭제 예정
    private List<QuarterEntryDTO> quarterEntryList; // TODO 삭제 예정
    private List<PlayerInfoDTO> playerList;

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


    public QuarterEntryInfoDTO playerList(List<PlayerInfoDTO> playerList) {
        this.playerList = playerList;
        return this;
    }
}
