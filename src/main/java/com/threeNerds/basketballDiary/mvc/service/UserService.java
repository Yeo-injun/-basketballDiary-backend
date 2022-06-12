package com.threeNerds.basketballDiary.mvc.service;

import com.threeNerds.basketballDiary.exception.CustomException;
import com.threeNerds.basketballDiary.exception.Error;
import com.threeNerds.basketballDiary.mvc.domain.User;
import com.threeNerds.basketballDiary.mvc.dto.loginUser.PasswordDTO;
import com.threeNerds.basketballDiary.mvc.dto.user.user.FindAllUserDTO;
import com.threeNerds.basketballDiary.mvc.dto.user.user.UpdateUserDTO;
import com.threeNerds.basketballDiary.mvc.dto.user.user.UserDTO;
import com.threeNerds.basketballDiary.mvc.repository.UserRepository;
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

    @Transactional
    public Long findSeq(String userId){
        return userRepository.findSeq(userId);
    }

    @Transactional
    public User findUser(Long id) {
        return userRepository.findUser(id);
    }
    @Transactional
    public Long createMember(User user) {
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

    @Transactional
    public List<UserDTO> findAllUser(FindAllUserDTO findAllUserDTO){
        return userRepository.findAllUser(findAllUserDTO);
    }

    public void checkDuplicationUserId(User checkForDuplication)
    {
        User user = userRepository.findUserByUserId(checkForDuplication);
        if (user != null) {
            throw new CustomException(Error.DUPLICATE_USER_ID);
        }

    }

    @Transactional
    public void updatePassword(PasswordDTO passwordDTO){
        userRepository.updatePassword(passwordDTO);
    }
}
