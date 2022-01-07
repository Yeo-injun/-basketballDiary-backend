package com.threeNerds.basketballDiary.mvc.service.impl;

import com.threeNerds.basketballDiary.mvc.domain.Member;
import com.threeNerds.basketballDiary.mvc.repository.LoginRepository;
import com.threeNerds.basketballDiary.mvc.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 어떻게 mybatis로 넘겨주는지...?Map 형태로?
     */
    public void createMember(Member member) {
        Map<String,Object> sqlParam = new HashMap<>();
        sqlParam.put("memberId",member.getMemberId());
        sqlParam.put("password",member.getPassword());
        sqlParam.put("city",member.getAddress().getCity());
        sqlParam.put("street",member.getAddress().getStreet());
        sqlParam.put("zipCode",member.getAddress().getZipCode());
        sqlParam.put("phoneNumber",member.getPhoneNumber());

        memberRepository.saveMember(sqlParam);
    }
}
