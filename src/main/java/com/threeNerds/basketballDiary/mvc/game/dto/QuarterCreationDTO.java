package com.threeNerds.basketballDiary.mvc.game.dto;

import java.util.List;

/**
 * 쿼터를 생성하기 위해 사용되는 DTO.
 * 하위 DTO를 포함합니다.
 */
public class QuarterCreationDTO {
    // 경기기록 DTO (단건)
    TeamRecordDTO teamRecordDTO;
    // 선수기록 DTO (목록)
    List<PlayerRecordDTO> playerRecordDTOList;
}
