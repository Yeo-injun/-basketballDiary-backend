package com.threeNerds.basketballDiary.mvc.dto.pagination;

import com.threeNerds.basketballDiary.mvc.dto.myTeam.myTeam.MyTeamDTO;
import com.threeNerds.basketballDiary.mvc.dto.team.team.TeamDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class PaginatedMyTeamDTO {
    private PagerDTO pagerDTO;
    private List<MyTeamDTO> myTeamDTOList;

    public PaginatedMyTeamDTO(PagerDTO pagerDTO, List<MyTeamDTO> myTeamDTOList) {
        this.pagerDTO = pagerDTO;
        this.myTeamDTOList = myTeamDTOList;
    }
}
