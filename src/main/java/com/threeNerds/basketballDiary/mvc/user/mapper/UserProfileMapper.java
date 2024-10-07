package com.threeNerds.basketballDiary.mvc.user.mapper;


import com.threeNerds.basketballDiary.mvc.user.mapper.dto.MyProfileDTO;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserProfileMapper {
    /**-------------------------
     * UserSeq로 프로필 정보 조회
     **-------------------------*/
    MyProfileDTO findMyProfile( Long userSeq );
}
