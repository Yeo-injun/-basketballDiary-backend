package com.threeNerds.basketballDiary.mvc.myTeam.service.dto;

import com.threeNerds.basketballDiary.mvc.myTeam.dto.TeamInfoDTO;
import com.threeNerds.basketballDiary.mvc.team.dto.TeamRegularExerciseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyTeamInfoCommand {

    private Long teamSeq;
    private TeamInfoDTO teamInfo;
    private List<TeamRegularExerciseDTO> teamRegularExercises;
    private MultipartFile teamLogoImage;

}