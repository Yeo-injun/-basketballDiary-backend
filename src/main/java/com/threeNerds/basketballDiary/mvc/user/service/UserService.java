package com.threeNerds.basketballDiary.mvc.user.service;

import com.threeNerds.basketballDiary.mvc.user.dto.CmnUserDTO;
import com.threeNerds.basketballDiary.mvc.user.dto.UserDTO;
import com.threeNerds.basketballDiary.mvc.user.repository.UserRepository;
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
 * </pre>
 */

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public List<UserDTO> searchUsers(CmnUserDTO findUserCond)
    {
        // TODO 페이징 처리 추가
        return userRepository.findUserByUserNameOrEmail(findUserCond);
    }
}
