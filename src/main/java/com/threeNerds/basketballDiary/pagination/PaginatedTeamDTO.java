package com.threeNerds.basketballDiary.pagination;

import com.threeNerds.basketballDiary.mvc.team.dto.TeamDTO;
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
