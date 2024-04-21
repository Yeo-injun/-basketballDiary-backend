package com.threeNerds.basketballDiary.mvc.game.controller.response;

import com.threeNerds.basketballDiary.http.ResponseJsonBody;
import com.threeNerds.basketballDiary.mvc.game.dto.GameJoinTeamMemberDTO;
import com.threeNerds.basketballDiary.mvc.game.dto.GameRecorderCandidateDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class GetGameRecorderCandidatesResponse {

    private final List<GameRecorderCandidateDTO> candidates;

    public GetGameRecorderCandidatesResponse( List<GameRecorderCandidateDTO> candidates ) {
        this.candidates = candidates;
    }
}
