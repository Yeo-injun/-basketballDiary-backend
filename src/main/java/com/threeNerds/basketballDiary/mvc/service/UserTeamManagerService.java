package com.threeNerds.basketballDiary.mvc.service;

import com.threeNerds.basketballDiary.constant.JoinRequestStateCode;
import com.threeNerds.basketballDiary.constant.JoinRequestTypeCode;
import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.Error;
import com.threeNerds.basketballDiary.mvc.domain.TeamJoinRequest;
import com.threeNerds.basketballDiary.mvc.dto.loginUser.userTeamManager.JoinRequestDTO;
import com.threeNerds.basketballDiary.mvc.dto.loginUser.CmnLoginUserDTO;
import com.threeNerds.basketballDiary.mvc.repository.TeamJoinRequestRepository;
import com.threeNerds.basketballDiary.mvc.repository.UserTeamManagerRepository;
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
    private final UserTeamManagerRepository userTeamManagerRepository;

    // 농구팀에 가입요청 보내기
    public void sendJoinRequestToTeam(CmnLoginUserDTO loginUserDTO) {

        TeamJoinRequest joinRequestInfo = TeamJoinRequest.createJoinRequest(loginUserDTO);

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
//    public List<JoinRequestDTO> searchJoinRequestsAll(JoinRequestDTO joinRequestDTO)
//    {
//        List<JoinRequestDTO> joinRequestDTOList = teamJoinRequestRepository.findAllJoinRequests(joinRequestDTO);
//        for (JoinRequestDTO joinRequest : joinRequestDTOList)
//        {
//            String typeCode = joinRequest.getJoinRequestTypeCode();
//            String stateCode = joinRequest.getJoinRequestStateCode();
//
//            joinRequest.joinRequestTypeCodeName(typeCode);
//            joinRequest.joinRequestStateCodeName(stateCode);
//        }
//        return joinRequestDTOList;
//    }

    // 사용자가 가입요청을 보낸 팀 목록을 조회
    public List<JoinRequestDTO> getJoinRequestsTo(CmnLoginUserDTO loginUserDTO)
    {
        JoinRequestDTO joinRequestDTO = new JoinRequestDTO()
                .userSeq(loginUserDTO.getUserSeq())
                .joinRequestTypeCode(JoinRequestTypeCode.JOIN_REQUEST.getCode());

        List<JoinRequestDTO> joinRequestDTOList = userTeamManagerRepository.findJoinRequestsByType(joinRequestDTO);
        for (JoinRequestDTO joinRequest : joinRequestDTOList)
        {
            joinRequest.setCodeNameByInstanceCodeValue();
        }
        return joinRequestDTOList;
    }

    // 사용자가 초대 받은 팀 목록을 조회
    public List<JoinRequestDTO> getJoinRequestsFrom(CmnLoginUserDTO loginUserDTO)
    {
        JoinRequestDTO joinRequestDTO = new JoinRequestDTO()
                .userSeq(loginUserDTO.getUserSeq())
                .joinRequestTypeCode(JoinRequestTypeCode.INVITATION.getCode());

        List<JoinRequestDTO> joinRequestDTOList = userTeamManagerRepository.findJoinRequestsByType(joinRequestDTO);
        for (JoinRequestDTO joinRequest : joinRequestDTOList)
        {
            joinRequest.setCodeNameByInstanceCodeValue();
        }
        return joinRequestDTOList;
    }

    // 사용자가 가입요청한 건을 취소상태로 바꾸는 API (데이터를 삭제하지는 않음)
    public void cancelJoinRequest(CmnLoginUserDTO loginUserDTO)
    {
        TeamJoinRequest joinRequestCancel = TeamJoinRequest.cancelJoinRequest(loginUserDTO);

        // TODO 가입요청 취소 Request를 보낸 사용자가 가입요청을 취소하는 것인지도 확인해야 하는지??
       boolean isSuccess = teamJoinRequestRepository.updateJoinRequestState(joinRequestCancel) == 1 ? true : false;
        if (!isSuccess)
        {
            throw new CustomException(Error.JOIN_REQUEST_NOT_FOUND);
        }
    }
}
