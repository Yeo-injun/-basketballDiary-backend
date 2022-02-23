package com.threeNerds.basketballDiary.mvc.repository;

import com.threeNerds.basketballDiary.mvc.domain.TeamMember;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TeamMemberRepository {
    TeamMember findByTeamMemberSeq(Long teamMemberSeq);
}
