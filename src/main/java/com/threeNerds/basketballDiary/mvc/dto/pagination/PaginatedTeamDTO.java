package com.threeNerds.basketballDiary.mvc.dto.pagination;

import com.threeNerds.basketballDiary.mvc.dto.team.team.TeamDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class PaginatedTeamDTO {
    private PagerDTO pagerDTO;
    private List<TeamDTO> teamDTOList;

    public PaginatedTeamDTO(PagerDTO pagerDTO, List<TeamDTO> teamDTOList) {
        this.pagerDTO = pagerDTO;
        this.teamDTOList = teamDTOList;
    }

}
