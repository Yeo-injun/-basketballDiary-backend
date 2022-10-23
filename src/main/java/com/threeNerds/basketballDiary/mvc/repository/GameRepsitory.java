package com.threeNerds.basketballDiary.mvc.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GameRepsitory {

    void deleteGame(Long gameSeq);
}
