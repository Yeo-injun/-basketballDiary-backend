package com.threeNerds.basketballDiary.mvc.user.repository.dto;


import com.threeNerds.basketballDiary.mvc.user.dto.MyProfileDTO;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface ProfileQueryRepository {
    /**-------------------------
     * UserSeq로 프로필 정보 조회
     **-------------------------*/
    MyProfileDTO findMyProfile( Long userSeq );
}
