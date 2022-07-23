package com.threeNerds.basketballDiary.mvc.dto.team.team;

import com.threeNerds.basketballDiary.pagination.PagerDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class PaginationTeamDTO {
    private PagerDTO pagerDTO;
    private List<TeamDTO> teamDTOList;

    public PaginationTeamDTO(PagerDTO pagerDTO, List<TeamDTO> teamDTOList) {
        this.pagerDTO = pagerDTO;
        this.teamDTOList = teamDTOList;
    }
//    public PaginationTeamDTO teamDTOList(List<TeamDTO> teamDTOList) {
//        this.teamDTOList = teamDTOList;
//        return this;
//    }
//
//    public PaginationTeamDTO pagerDTO(PagerDTO pagerDTO) {
//        this.pagerDTO = pagerDTO;
//        return this;
//    }
}
