package com.threeNerds.basketballDiary.mvc.controller;

import com.threeNerds.basketballDiary.mvc.dto.ResponseMyTeamProfileDTO;
import com.threeNerds.basketballDiary.mvc.service.TeamMemberService;
import com.threeNerds.basketballDiary.session.SessionUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static com.threeNerds.basketballDiary.session.SessionConst.LOGIN_MEMBER;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profile")
public class ProfileController {

    private final TeamMemberService teamMemberService;

    /**
     * 소속팀 개인프로필 수정데이터 조회
     */
    @GetMapping("/myTeams/{teamSeq}/profile")
    public ResponseMyTeamProfileDTO findMyTeamsProfile(
            @SessionAttribute(value = LOGIN_MEMBER,required = false) SessionUser sessionDTO,
            @PathVariable Long teamSeq){

        Long id = sessionDTO.getUserSeq();

        FindMyTeamProfileDTO findMyTeamProfileDTO = new FindMyTeamProfileDTO()
                .userSeq(id)
                .teamSeq(teamSeq);

        ResponseMyTeamProfileDTO myTeamProfileDTO = teamMemberService.findProfile(findMyTeamProfileDTO);

        return myTeamProfileDTO;
    }
    @Getter
    public static class FindMyTeamProfileDTO {
        private Long userSeq;
        private Long teamSeq;

        public FindMyTeamProfileDTO userSeq(Long userSeq){
            this.userSeq=userSeq;
            return this;
        }
        public FindMyTeamProfileDTO teamSeq(Long teamSeq){
            this.teamSeq=teamSeq;
            return this;
        }
    }

    /**
     * 소속팀 개인프로필 수정
     */
    @PatchMapping("/myTeams/{teamSeq}/profile")
    public String modifyMyTeamsProfile(
            @SessionAttribute(value = LOGIN_MEMBER,required = false) SessionUser sessionDTO,
            @PathVariable Long teamSeq,
            @RequestBody BackNumber backNumber){

        Long id = sessionDTO.getUserSeq();

        FindMyTeamProfileDTO findMyTeamProfileDTO = new FindMyTeamProfileDTO()
                .userSeq(id)
                .teamSeq(teamSeq);

        ModifyMyTeamProfileDTO myTeamProfileDTO = new ModifyMyTeamProfileDTO()
                .findMyTeamProfileDTO(findMyTeamProfileDTO)
                .backNumber(backNumber.getBackNumber());

        teamMemberService.updateMyTeamProfile(myTeamProfileDTO);
        return "ok";
    }

    @Getter
    public static class BackNumber{
        private String backNumber;
    }

    @Getter
    public static class ModifyMyTeamProfileDTO{
        private FindMyTeamProfileDTO findMyTeamProfileDTO;
        private String backNumber;

        public ModifyMyTeamProfileDTO findMyTeamProfileDTO(FindMyTeamProfileDTO findMyTeamProfileDTO){
            this.findMyTeamProfileDTO = findMyTeamProfileDTO;
            return this;
        }
        public ModifyMyTeamProfileDTO backNumber(String backNumber){
            this.backNumber = backNumber;
            return this;
        }
    }

    /**
     * 소속팀 탈퇴
     */
    @DeleteMapping("/myTeams/{teamSeq}")
    public String deleteMyTeamsProfile(
            @SessionAttribute(value = LOGIN_MEMBER,required = false) SessionUser sessionDTO,
            @PathVariable Long teamSeq)
    {
        Long id = sessionDTO.getUserSeq();

        FindMyTeamProfileDTO findMyTeamProfileDTO = new FindMyTeamProfileDTO()
                .userSeq(id)
                .teamSeq(teamSeq);
        teamMemberService.deleteMyTeamProfile(findMyTeamProfileDTO);
        return "ok";
    }
}
