package com.threeNerds.basketballDiary.mvc.game.service.dto;


import com.threeNerds.basketballDiary.mvc.game.dto.PlayerInfoDTO;
import com.threeNerds.basketballDiary.pagination.Pagination;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameJoinPlayerResult {

    private Long gameSeq;
    private Long teamSeq;
    private Pagination pagination;
    private List<PlayerInfoDTO> players;

}
