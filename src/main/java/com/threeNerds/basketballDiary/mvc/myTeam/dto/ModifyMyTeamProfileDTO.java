package com.threeNerds.basketballDiary.mvc.myTeam.dto;

import lombok.Getter;

@Getter
public class ModifyMyTeamProfileDTO{
    private FindMyTeamProfileDTO findMyTeamProfileDTO;
    private String backNumber;

    public ModifyMyTeamProfileDTO findMyTeamProfileDTO(FindMyTeamProfileDTO findMyTeamProfileDTO){
        this.findMyTeamProfileDTO = findMyTeamProfileDTO;
        return this;
    }
    public ModifyMyTeamProfileDTO backNumber(String backNumber){
        this.backNumber = backNumber;
        return this;
    }
}
