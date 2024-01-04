package com.threeNerds.basketballDiary.mvc.user.controller;

import com.threeNerds.basketballDiary.exception.error.DomainErrorResponse;
import com.threeNerds.basketballDiary.http.ResponseJsonBody;
import com.threeNerds.basketballDiary.mvc.user.dto.SearchUsersExcludingTeamMember.request.SearchUsersExcludingTeamMemberRequest;
import com.threeNerds.basketballDiary.mvc.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * ... 수행하는 Controller
 * @author 책임자 작성
 *
 * issue and history
 * <pre>
 * 2022.02.08 여인준 : 소스코드 생성
 * 2022.03.13 이성주 : LoginController 통합
 * </pre>
 */

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "UserController")
public class UserController {

    private final UserService userService;

    /**
     * API006 사용자 검색
     * 23.05.07 여인준 : 팀원 제외하고 조회되게끔 변경
     */
    @Operation(summary = "사용자 검색 API",description = "사용자 검색",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404", description = "팀원을 찾지 못하였습니다.",content = @Content(schema = @Schema(implementation = DomainErrorResponse.class)))
            })
    @GetMapping("/exclusion/team/{teamSeq}")
    public ResponseEntity<?> searchUsersExcludingTeamMember (
            @PathVariable Long teamSeq,
            @RequestParam( required = false ) String userName,
            @RequestParam( required = false ) String email
    ){
        SearchUsersExcludingTeamMemberRequest reqBody = new SearchUsersExcludingTeamMemberRequest( teamSeq, userName, email );
        ResponseJsonBody resBody = userService.searchUsersExcludingTeamMember( reqBody );
        return ResponseEntity.ok().body(resBody);
    }

}
