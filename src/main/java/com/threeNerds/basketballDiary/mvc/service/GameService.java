package com.threeNerds.basketballDiary.mvc.service;

import com.threeNerds.basketballDiary.mvc.repository.GameRepsitory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class GameService {

    private final GameRepsitory gameRepsitory;

    public void DeleteGame(Long gameSeq){
        gameRepsitory.deleteGame(gameSeq);
    }
}
