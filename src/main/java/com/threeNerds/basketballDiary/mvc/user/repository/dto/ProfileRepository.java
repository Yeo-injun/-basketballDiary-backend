package com.threeNerds.basketballDiary.mvc.user.repository.dto;


import com.threeNerds.basketballDiary.mvc.user.dto.MyProfileDTO;
import com.threeNerds.basketballDiary.mvc.user.dto.UserInqCondDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProfileRepository {
    /**-------------------------
     * UserSeq로 프로필 정보 조회
     **-------------------------*/
    MyProfileDTO findMyProfile( Long userSeq );
    /**-------------------------
     * 사용자 프로필 목록 조회 ( 페이징 )
     **-------------------------*/
    List<MyProfileDTO> findAllPagingProfile( UserInqCondDTO inqCond );
}
