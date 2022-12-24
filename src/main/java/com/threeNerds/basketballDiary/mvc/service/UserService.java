package com.threeNerds.basketballDiary.mvc.service;

import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.Error;
import com.threeNerds.basketballDiary.mvc.domain.User;
import com.threeNerds.basketballDiary.mvc.dto.loginUser.PasswordUpdateDTO;
import com.threeNerds.basketballDiary.mvc.dto.user.CmnUserDTO;
import com.threeNerds.basketballDiary.mvc.dto.user.user.UpdateUserDTO;
import com.threeNerds.basketballDiary.mvc.dto.user.user.UserDTO;
import com.threeNerds.basketballDiary.mvc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.util.List;
import java.util.Optional;

import static com.threeNerds.basketballDiary.exception.Error.INCORRECT_PASSWORD;

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

    @Transactional
    public UserDTO findUser(Long userSeq)
    {
        User findUser = Optional.ofNullable(userRepository.findUser(userSeq))
                                .orElseThrow(()-> new CustomException(Error.USER_NOT_FOUND));
        return UserDTO.getInstance(findUser);
    }

    @Transactional
    public Long createMember(CmnUserDTO userDTO)
    {
        User user = User.createForRegistration(userDTO);
        userRepository.saveUser(user);
        return user.getUserSeq();
    }

    @Transactional
    public void updateUser(UpdateUserDTO user){
        userRepository.updateUser(user);
    }

    @Transactional
    public void deleteUser(String id) {
        userRepository.deleteUser(id);
    }

    public void checkDuplicationUserId(User checkUser)
    {
        User user = userRepository.findUserByUserId(checkUser.getUserId());
        if (user != null) {
            throw new CustomException(Error.DUPLICATE_USER_ID);
        }
    }

    @Transactional
    public void updatePassword(PasswordUpdateDTO passwordUpdateDTO)
    {
        User findUser = Optional.ofNullable(userRepository.findUser(passwordUpdateDTO.getUserSeq()))
                .orElseThrow(()-> new CustomException(Error.USER_NOT_FOUND));

        String prevPassword = Optional.ofNullable(passwordUpdateDTO.getPrevPassword())
                                     .orElseThrow(()-> new CustomException(INCORRECT_PASSWORD));

        if(!prevPassword.equals(findUser.getPassword())) {
            throw new CustomException(INCORRECT_PASSWORD);
        }
        userRepository.updatePassword(passwordUpdateDTO);
    }

    public List<UserDTO> findUserByCond(CmnUserDTO findUserCond)
    {
        // TODO 페이징 처리 추가
        return userRepository.findUserByUserNameOrEmail(findUserCond);
    }
}
