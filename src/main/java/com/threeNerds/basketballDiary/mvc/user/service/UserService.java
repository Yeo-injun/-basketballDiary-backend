package com.threeNerds.basketballDiary.mvc.user.service;

import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.error.DomainErrorType;
import com.threeNerds.basketballDiary.http.ResponseJsonBody;
import com.threeNerds.basketballDiary.mvc.auth.controller.request.CreateUserRequest;
import com.threeNerds.basketballDiary.mvc.user.domain.User;
import com.threeNerds.basketballDiary.mvc.user.dto.CmnUserDTO;
import com.threeNerds.basketballDiary.mvc.user.dto.SearchUsersExcludingTeamMember.request.SearchUsersExcludingTeamMemberRequest;
import com.threeNerds.basketballDiary.mvc.user.dto.SearchUsersExcludingTeamMember.response.SearchUsersExcludingTeamMemberResponse;
import com.threeNerds.basketballDiary.mvc.user.dto.UserDTO;
import com.threeNerds.basketballDiary.mvc.user.dto.UserInqCondDTO;
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

    public SearchUsersExcludingTeamMemberResponse searchUsersExcludingTeamMember( SearchUsersExcludingTeamMemberRequest reqBody ) {

        UserInqCondDTO inqCond = new UserInqCondDTO()
                                    .teamSeq( reqBody.getTeamSeq() )
                                    .userName( reqBody.getUserName() )
                                    .email( reqBody.getEmail() );
        // TODO 페이징 처리 추가
        List<UserDTO> users = userRepository.findAllUsersExcludingTeamMemberByUserNameOrEmail( inqCond );
        return new SearchUsersExcludingTeamMemberResponse( users );
    }


    public boolean checkUserIdAvailable( String userId ) {
        return isUserIdAvailable( userId );
    }

    private boolean isUserIdAvailable( String userId ) {
        return null == userRepository.findUserByUserId( userId );
    }

    public void createMembership( CreateUserRequest request ) {
        if ( !isUserIdAvailable( request.getUserId() ) ) {
            throw new CustomException( DomainErrorType.NOT_AVAILABLE_USER_ID );
        }
        User user = User.createForRegistration( request );
        userRepository.saveUser( user );
    }


}
