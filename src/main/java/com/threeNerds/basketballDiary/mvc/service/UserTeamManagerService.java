package com.threeNerds.basketballDiary.mvc.service;

import com.threeNerds.basketballDiary.constant.JoinRequestStateCode;
import com.threeNerds.basketballDiary.constant.JoinRequestTypeCode;
import com.threeNerds.basketballDiary.mvc.domain.TeamJoinRequest;
import com.threeNerds.basketballDiary.mvc.dto.JoinRequestDTO;
import com.threeNerds.basketballDiary.mvc.repository.TeamJoinRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 사용자가 팀의 구성원으로서 관련된 업무를 수행하기 위한 Service
 * @author 여인준
 *
 * issue and history
 * <pre>
 * 2022.02.08 여인준 : 소스코드 생성
 * 2022.02.22 여인준 : CodeEnum에서 Code로 Name가져오는 메소드 구현 및 적용 완료
 * </pre>
 */

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserTeamManagerService {

    private final TeamJoinRequestRepository teamJoinRequestRepository;

    // 농구팀에 가입요청 보내기
    public void sendJoinRequestToTeam(JoinRequestDTO joinRequest) {
        // TODO teamJoinRequest에 필요한 데이터 추가 세팅
        // TODO 불변객체 사용시 고려사항(Builder패턴을 사용한 객체생성)
        // DTO, Entity객체를 불변객체로 만들경우 생성할때만 값을 변경할 수 있기 때문에
        // 이전 레이어에서 넘겨받은 객체에 값을 추가하지 못함.
        // 이때문에 넘겨받은 객체에 값을 추가할 경우 새로 객체를 생성해야 함. >> 이는 객체를 불필요하게 생성하게 됨(메모리 문제)
        TeamJoinRequest joinRequestInfo = TeamJoinRequest.builder()
                        .userSeq(joinRequest.getUserSeq())
                        .teamSeq(joinRequest.getTeamSeq())
                        .joinRequestTypeCode(JoinRequestTypeCode.JOIN_REQUEST.getCode())
                        .joinRequestStateCode(JoinRequestStateCode.WAITING.getCode())
                        .build();

        // 초대-가입요청 존재여부 확인 : 대기중인 가입요청 혹은 초대가 있을 경우 중복가입요청 방지
        TeamJoinRequest prevJoinReq = teamJoinRequestRepository.checkJoinRequest(joinRequestInfo);
        boolean isExistJoinRequest = prevJoinReq != null
                ? true
                : false;
        if (isExistJoinRequest)
        {
            String prevJoinReqTypeName = JoinRequestTypeCode.getName(prevJoinReq.getJoinRequestTypeCode());
            log.info("==== 이미 {}가 있습니다. ====", prevJoinReqTypeName);
            return; // TODO 에러를 던지거나 다른 것을 리턴하기
        }

        // 초대-가입요청이 없을경우에만 INSERT
        teamJoinRequestRepository.createJoinRequest(joinRequestInfo);
    }

    // 농구팀 가입요청 및 초대목록 조회하기
    public List<JoinRequestDTO> searchJoinRequestsAll(JoinRequestDTO joinRequestDTO)
    {
        // TODO 코드명 할당하기
        List<JoinRequestDTO> joinRequestDTOList = teamJoinRequestRepository.findAllJoinRequests(joinRequestDTO);
        return joinRequestDTOList;
    }
}
