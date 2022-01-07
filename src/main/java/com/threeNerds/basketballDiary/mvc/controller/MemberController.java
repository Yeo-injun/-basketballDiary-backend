package com.threeNerds.basketballDiary.mvc.controller;

import com.threeNerds.basketballDiary.mvc.domain.Member;
import com.threeNerds.basketballDiary.mvc.dto.CreateMemberDTO;
import com.threeNerds.basketballDiary.mvc.dto.LoginMemberDTO;
import com.threeNerds.basketballDiary.mvc.service.impl.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(){
        return "members/createForm";
    }
    @PostMapping("/members/new")
    public String create(@RequestBody @Valid CreateMemberDTO createMemberDTO){
        Member member = new Member();
        member.setMemberId(createMemberDTO.getMemberId());
        member.setAddress(createMemberDTO.getAddress());
        member.setPassword(createMemberDTO.getPassword());
        member.setPhoneNumber(createMemberDTO.getPhoneNumber());
        memberService.createMember(member);
        return "/";
    }

}
