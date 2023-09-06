package com.threeNerds.basketballDiary.file.controller;

import com.threeNerds.basketballDiary.file.ImageProvider;
import com.threeNerds.basketballDiary.file.ImageUploader;
import com.threeNerds.basketballDiary.interceptor.Auth;
import com.threeNerds.basketballDiary.mvc.game.service.GameRecordManagerService;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.*;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.getManagers.request.GetManagersRequest;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.getManagers.response.GetManagersResponse;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.getMyTeamProfile.request.GetMyTeamProfileRequest;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.getMyTeamProfile.response.GetMyTeamProfileResponse;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.getMyTeamProfile.response.MyTeamProfileDTO;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.getTeamMembers.request.GetTeamMembersRequest;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.getTeamMembers.response.GetTeamMembersResponse;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.modifyMyTeamProfile.request.ModifyMyTeamProfileRequest;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.searchAllTeamMembers.request.SearchAllTeamMembersRequest;
import com.threeNerds.basketballDiary.mvc.myTeam.dto.searchAllTeamMembers.response.SearchAllTeamMembersResponse;
import com.threeNerds.basketballDiary.mvc.myTeam.service.MyTeamService;
import com.threeNerds.basketballDiary.mvc.myTeam.service.TeamMemberManagerService;
import com.threeNerds.basketballDiary.mvc.myTeam.service.TeamMemberService;
import com.threeNerds.basketballDiary.mvc.team.dto.PlayerDTO;
import com.threeNerds.basketballDiary.pagination.PaginatedMyTeamDTO;
import com.threeNerds.basketballDiary.session.SessionUser;
import com.threeNerds.basketballDiary.utils.SessionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.List;

import static com.threeNerds.basketballDiary.constant.HttpResponseConst.RESPONSE_OK;
import static com.threeNerds.basketballDiary.constant.UserAuthConst.*;
import static com.threeNerds.basketballDiary.utils.SessionUtil.LOGIN_USER;

/**
 * 이미지와 관련된 요청을 Controller
 *
 * @author 여인준
 * <p>
 * issue and history
 * <pre>
 * 2023.09.05 ( 화 ) 여인준 : 최초 작성
 * </pre>
 */


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
public class ImageController {

    /**--------------------------------------
     * Components
     **--------------------------------------*/
    private final ImageProvider provider;

    /**
     * 23.09.05 여인준
     * 파일로 저장된 이미지 조회
     * >> /images 이하로 오는 모든 request는 해당 메소드에 매핑됨.
     * >> /images 이하의 url이 이미지 저장경로를 의미
     */
    @GetMapping("/**")
    public ResponseEntity<Resource> getImage( HttpServletRequest request ) throws MalformedURLException {

        String requestUrl = request.getRequestURI();
        // TODO 아래 데이터 차이 확인 필요
        log.debug( request.getPathInfo() );
        log.debug( request.getRequestURI() );
        log.debug( request.getRequestURL().toString() );
        String imageSaveUrl = requestUrl.replace( "/images", "" );
        return ResponseEntity.ok( provider.provide( imageSaveUrl ) );
    }

}
