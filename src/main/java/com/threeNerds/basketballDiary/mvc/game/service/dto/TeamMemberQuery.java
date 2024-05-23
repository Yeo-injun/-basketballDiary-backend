package com.threeNerds.basketballDiary.mvc.game.service.dto;

import com.threeNerds.basketballDiary.mvc.game.dto.GameOpponentDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.MemberDTO;
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
public class TeamMemberQuery {

    Integer pageNo;     /* 페이징 : 현재 페이지 */
    Long teamSeq;       /* 팀Seq */
    String playerName;  /* 선수이름 */

    /**
     * 결과값
     */
    @Getter
    public class Result {
        Pagination pagination;
        List<MemberDTO> teamMembers;

        Result( Pagination pagination, List<MemberDTO> teamMembers ) {
            this.pagination = pagination;
            this.teamMembers  = teamMembers;
        }
    }

    public TeamMemberQuery.Result buildResult( Pagination pagination, List<MemberDTO> teamMembers ) {
        return new Result( pagination, teamMembers );
    }

}
