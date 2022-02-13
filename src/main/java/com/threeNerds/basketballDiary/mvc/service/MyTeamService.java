package com.threeNerds.basketballDiary.mvc.service;

import com.threeNerds.basketballDiary.mvc.dto.MyTeamDTO;
import com.threeNerds.basketballDiary.mvc.repository.MyTeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 소속팀에서 팀원관리 및 소속팀정보 관리 등의 업무를 수행하는 Service
 * @author 책임자 작성
 *
 * issue and history
 * <pre>
 * 2022.02.08 여인준 : 소스코드 생성
 * 2022.02.13 강창기 : 소속팀 목록조회 구현
 * </pre>
 */

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MyTeamService {

    private final MyTeamRepository myTeamRepository;

    public List<MyTeamDTO> findTeams(Long userSeq) {
        if(userSeq == null)
            throw new NullPointerException("userSeq");

        List<MyTeamDTO> myTeamList = myTeamRepository.findAllByUserSeq(userSeq);

        myTeamList.forEach(myTeamDTO -> log.info("teamName = {}, authCode = {}"
                , myTeamDTO.getTeamName(), myTeamDTO.getTeamAuthCode()));
        return myTeamList;
    }

    public MyTeamDTO findTeam(Long userSeq, Long teamSeq) {
        if(userSeq == null)
            throw new NullPointerException("userSeq");
        if(teamSeq == null)
            throw new NullPointerException("teamSeq");

        MyTeamDTO myTeam = myTeamRepository.findByUserSeqAndTeamSeq(userSeq, teamSeq);

        if(myTeam == null)
            throw new NullPointerException("소속팀의 해당 팀 정보가 존재하지 않습니다.");

        log.info("teamName = {}", myTeam.getTeamName());
        return myTeam;
    }
}
