package com.threeNerds.basketballDiary.mvc.repository;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface MemberRepository {
    void saveMember(Map<String,Object> sqlParam);
}
