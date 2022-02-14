package com.threeNerds.basketballDiary.mvc.service;

import com.threeNerds.basketballDiary.mvc.domain.JoinRequestStateCode;
import com.threeNerds.basketballDiary.mvc.domain.JoinRequestTypeCode;
import com.threeNerds.basketballDiary.mvc.domain.TeamJoinRequest;
import com.threeNerds.basketballDiary.mvc.repository.TeamJoinRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 사용자가 팀의 구성원으로서 관련된 업무를 수행하기 위한 Service
 * @author 여인준
 *
 * issue and history
 * <pre>
 * 2022.02.08 여인준 : 소스코드 생성
 * </pre>
 */

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserTeamManagerService {

    private final TeamJoinRequestRepository teamJoinRequestRepository;

    // 농구팀에 가입요청 보내기
    public void sendJoinRequestToTeam(TeamJoinRequest teamJoinRequest) {
        // TODO teamJoinRequest에 필요한 데이터 추가 세팅
        // TODO 불변객체 사용시 고려사항(Builder패턴을 사용한 객체생성)
        // DTO, Entity객체를 불변객체로 만들경우 생성할때만 값을 변경할 수 있기 때문에
        // 이전 레이어에서 넘겨받은 객체에 값을 추가하지 못함.
        // 이때문에 넘겨받은 객체에 값을 추가할 경우 새로 객체를 생성해야 함. >> 이는 객체를 불필요하게 생성하게 됨(메모리 문제)
        TeamJoinRequest joinRequest = TeamJoinRequest.builder()
                        .userSeq(teamJoinRequest.getUserSeq())
                        .teamSeq(teamJoinRequest.getTeamSeq())
                        .joinRequestTypeCode(JoinRequestTypeCode.JOIN_REQUEST.getCode())
                        .joinRequestStateCode(JoinRequestStateCode.WAITING.getCode())
                        .build();

        // TODO insert쿼리 호출
        teamJoinRequestRepository.createJoinRequest(joinRequest);
    }
}
