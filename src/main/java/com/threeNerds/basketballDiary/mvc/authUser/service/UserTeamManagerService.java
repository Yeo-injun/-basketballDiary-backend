package com.threeNerds.basketballDiary.mvc.authUser.service;

import com.threeNerds.basketballDiary.constant.code.type.JoinRequestTypeCode;
import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.mvc.authUser.service.dto.JoinRequestCommandDTO;
import com.threeNerds.basketballDiary.mvc.user.domain.User;
import com.threeNerds.basketballDiary.mvc.team.dto.TeamAuthDTO;
import com.threeNerds.basketballDiary.mvc.authUser.dto.CmnLoginUserDTO;
import com.threeNerds.basketballDiary.mvc.authUser.dto.JoinRequestDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.domain.TeamJoinRequest;
import com.threeNerds.basketballDiary.mvc.myTeam.domain.TeamMember;
import com.threeNerds.basketballDiary.mvc.myTeam.repository.TeamJoinRequestRepository;
import com.threeNerds.basketballDiary.mvc.myTeam.repository.TeamMemberRepository;
import com.threeNerds.basketballDiary.mvc.team.repository.TeamRepository;
import com.threeNerds.basketballDiary.mvc.user.repository.UserRepository;
import com.threeNerds.basketballDiary.mvc.authUser.repository.dto.UserTeamManagerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.threeNerds.basketballDiary.exception.error.DomainErrorType.*;

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
@Deprecated
public class UserTeamManagerService {

    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final TeamJoinRequestRepository teamJoinRequestRepository;
    private final UserTeamManagerRepository userTeamManagerRepository;

    // 사용자가 초대 받은 팀 목록을 조회
    public List<JoinRequestDTO> getJoinRequestsFrom(CmnLoginUserDTO loginUserDTO) {
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

}
