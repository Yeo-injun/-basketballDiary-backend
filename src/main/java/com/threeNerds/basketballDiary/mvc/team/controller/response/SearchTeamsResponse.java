package com.threeNerds.basketballDiary.mvc.team.controller.response;

import com.threeNerds.basketballDiary.mvc.team.dto.TeamDTO;
import com.threeNerds.basketballDiary.mvc.team.dto.TeamRegularExerciseDTO;
import com.threeNerds.basketballDiary.pagination.PagerDTO;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
public class SearchTeamsResponse {

    private PagerDTO pagination;
    private List<TeamDTO> teams;

    public SearchTeamsResponse(PagerDTO pagination, List<TeamDTO> teams) {
        this.pagination = pagination;
        this.teams = teams;
    }

}
