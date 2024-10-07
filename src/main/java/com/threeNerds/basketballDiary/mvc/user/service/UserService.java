package com.threeNerds.basketballDiary.mvc.user.service;

import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.error.DomainErrorType;
import com.threeNerds.basketballDiary.exception.error.SystemErrorType;
import com.threeNerds.basketballDiary.mvc.user.mapper.dto.UserQueryCondDTO;
import com.threeNerds.basketballDiary.mvc.user.mapper.UserProfileMapper;
import com.threeNerds.basketballDiary.mvc.user.mapper.UserMapper;
import com.threeNerds.basketballDiary.mvc.user.service.dto.PasswordCommand;
import com.threeNerds.basketballDiary.mvc.user.service.dto.ProfileCommand;
import com.threeNerds.basketballDiary.mvc.user.domain.User;

import com.threeNerds.basketballDiary.mvc.user.mapper.dto.MyProfileDTO;
import com.threeNerds.basketballDiary.mvc.user.mapper.dto.UserDTO;
import com.threeNerds.basketballDiary.mvc.user.domain.repository.UserRepository;
import com.threeNerds.basketballDiary.mvc.user.service.dto.MembershipCommand;
import com.threeNerds.basketballDiary.mvc.user.service.dto.UserQuery;
import com.threeNerds.basketballDiary.pagination.Pagination;
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

    /** 도메인 레포지토리 */
    private final UserRepository userRepository;

    /** 조회용 레포지토리 */
    private final UserMapper userQueryRepository;
    private final UserProfileMapper profileQueryRepository;

    /**
     * 회원 조회
     * - 소속 팀에 속하지 않은 회원
     */
    public UserQuery.Result getUsersExcludingTeamMembers( UserQuery query ) {
        Pagination pagination = Pagination.of( query.getPageNo(), 5 );

        UserQueryCondDTO inqCond = new UserQueryCondDTO()
                .pagination( pagination )
                .teamSeq(   query.getTeamSeq() )
                .userName(  query.getUserName() )
                .email(     query.getEmail() );

        List<UserDTO> usersWithoutTeamMember = userQueryRepository.findPaginationUsersWithoutTeamMembers( inqCond );
        if ( usersWithoutTeamMember.isEmpty() ) {
            return query.buildResult(
                pagination.getPages( 0 ),
                usersWithoutTeamMember
            );
        }
        return query.buildResult(
            pagination.getPages( userQueryRepository.findTotalCountUsersWithoutTeamMembers( inqCond ) ),
            usersWithoutTeamMember
        );
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
    public void withdrawalMembership( MembershipCommand command ) {
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


    /**
     * 프로필 조회
     */
    public MyProfileDTO getMyProfile( Long userSeq ) {
        MyProfileDTO profile = profileQueryRepository.findMyProfile( userSeq );

        if ( null == profile ) {
            throw new CustomException( DomainErrorType.USER_NOT_FOUND );
        }
        return profile;
    }


    /**
     * 프로필 수정
     */
    public void updateMyProfile( ProfileCommand profile ) {
        boolean isSuccessUpdateProfile = userRepository.updateProfile( User.ofUpdate( profile ) ) == 1;
        if ( !isSuccessUpdateProfile ) {
            throw new CustomException( SystemErrorType.NOT_FOUND_USER_FOR_UPDATE );
        }
    }

    /**
     * 비밀번호 변경
     */
    public void updatePassword( PasswordCommand command ) {
        User findUser = userRepository.findUser( command.getUserSeq() );

        if ( null == findUser ) {
            throw new CustomException( DomainErrorType.USER_NOT_FOUND );
        }

        if ( !findUser.checkAuthentication( command.getPrevPassword() ) ) {
            throw new CustomException( DomainErrorType.INCORRECT_PASSWORD );
        }

        userRepository.updatePassword( User.ofUpdate( command ) );
    }
}
