package com.threeNerds.basketballDiary.pagination;

import com.threeNerds.basketballDiary.mvc.myTeam.dto.MyTeamDTO;
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
