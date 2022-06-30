package com.threeNerds.basketballDiary.mvc.service;

import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.Error;
import com.threeNerds.basketballDiary.mvc.domain.User;
import com.threeNerds.basketballDiary.mvc.dto.TeamAuthDTO;
import com.threeNerds.basketballDiary.mvc.dto.loginUser.CmnLoginUserDTO;
import com.threeNerds.basketballDiary.mvc.dto.user.CmnUserDTO;
import com.threeNerds.basketballDiary.mvc.dto.user.user.LoginUserDTO;
import com.threeNerds.basketballDiary.mvc.repository.TeamMemberRepository;
import com.threeNerds.basketballDiary.mvc.repository.UserRepository;
import com.threeNerds.basketballDiary.session.SessionUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class LoginService {

    private final UserRepository userRepository;
    private final TeamMemberRepository teamMemberRepository;

    @Transactional
    public SessionUser login(LoginUserDTO loginUserDTO)
    {
        log.info("Check User id = {}", loginUserDTO.getUserId());
        User findLoginUser = userRepository.loginFindUser(loginUserDTO);

        if (findLoginUser == null) {
            throw new CustomException(Error.USER_NOT_FOUND);
        }

        SessionUser sessionUser = SessionUser.create(findLoginUser);
        /**만약 소속되어 있는 팀이 존재하지 않는다면 권한 정보 없이 return */
        Long myTeamCount = teamMemberRepository.findMyTeamCount(findLoginUser.getUserSeq());
        if(myTeamCount<=0){
            return sessionUser;
        }

        /** 권한정보 생성후 SesstionUser객체 return */
        List<TeamAuthDTO> authList = userRepository.findAuthList(findLoginUser);
        return sessionUser.updateAuthority(authList);
    }
}
