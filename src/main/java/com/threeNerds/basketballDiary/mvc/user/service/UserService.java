package com.threeNerds.basketballDiary.mvc.user.service;

import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.error.DomainErrorType;
import com.threeNerds.basketballDiary.exception.error.SystemErrorType;
import com.threeNerds.basketballDiary.mvc.user.domain.User;
import com.threeNerds.basketballDiary.mvc.user.dto.SearchUsersExcludingTeamMember.request.SearchUsersExcludingTeamMemberRequest;
import com.threeNerds.basketballDiary.mvc.user.dto.SearchUsersExcludingTeamMember.response.SearchUsersExcludingTeamMemberResponse;
import com.threeNerds.basketballDiary.mvc.user.dto.UserDTO;
import com.threeNerds.basketballDiary.mvc.user.dto.UserInqCondDTO;
import com.threeNerds.basketballDiary.mvc.user.repository.UserRepository;
import com.threeNerds.basketballDiary.mvc.user.service.dto.MembershipCommand;
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

    /**
     * 회원 조회
     * - 소속 팀에 속하지 않은 회원
     */
    public SearchUsersExcludingTeamMemberResponse searchUsersExcludingTeamMember( SearchUsersExcludingTeamMemberRequest reqBody ) {

        UserInqCondDTO inqCond = new UserInqCondDTO()
                                    .teamSeq( reqBody.getTeamSeq() )
                                    .userName( reqBody.getUserName() )
                                    .email( reqBody.getEmail() );
        // TODO 페이징 처리 추가
        List<UserDTO> users = userRepository.findAllUsersExcludingTeamMemberByUserNameOrEmail( inqCond );
        return new SearchUsersExcludingTeamMemberResponse( users );
    }


    /**
     * 회원 가입처리
     */
    public boolean checkUserIdAvailable( String userId ) {
        return isUserIdAvailable( userId );
    }

    /**
     * 회원 가입처리
     */
    public void createMembership( MembershipCommand command ) {
        if ( !isUserIdAvailable( command.getUserId() ) ) {
            throw new CustomException( DomainErrorType.NOT_AVAILABLE_USER_ID );
        }
        userRepository.saveUser( User.ofCreate( command ) );
    }

    /**
     * 회원 탈퇴처리
     */
    public void withdrawalMembership( com.threeNerds.basketballDiary.mvc.authUser.service.dto.MembershipCommand command ) {
        // TODO 회원탈퇴한 사용자 정보 별도 테이블로 데이터 이전 ( 비식별화 처리하여 )
        Long userSeq = command.getUserSeq();

        // 사용자 정보 조회
        User membership = userRepository.findUser( userSeq );
        if ( null == membership ) {
            throw new CustomException( SystemErrorType.NOT_FOUND_USER_FOR_WITHDRAWAL );
        }

        // 비밀번호 일치여부 확인
        if ( !membership.checkAuthentication( command.getPlainPassword() ) ) {
            throw new CustomException( DomainErrorType.INCORRECT_PASSWORD );
        }

        // USER테이블에서는 해당 row삭제
        userRepository.deleteUser( userSeq );
    }

    private boolean isUserIdAvailable( String userId ) {
        return null == userRepository.findUserByUserId( userId );
    }


}
