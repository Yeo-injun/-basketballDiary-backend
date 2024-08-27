package com.threeNerds.basketballDiary.mvc.myTeam.repository;

import com.threeNerds.basketballDiary.mvc.myTeam.dto.InvitationDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InvitationRepository {
    /**********
     * SELECT
     **********/
    List<InvitationDTO> findAllNotApproval( InvitationDTO param );
}
