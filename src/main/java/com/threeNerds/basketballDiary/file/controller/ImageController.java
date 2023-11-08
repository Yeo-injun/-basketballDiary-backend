package com.threeNerds.basketballDiary.file.controller;

import com.threeNerds.basketballDiary.file.ImageProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;

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
@RequestMapping("/image")
public class ImageController {
    /**--------------------------------------
     * Components
     **--------------------------------------*/
    private final ImageProvider provider;

    /**
     * 23.09.05 여인준
     * 파일로 저장된 이미지 조회
     * >> /image 이하로 오는 모든 request는 해당 메소드에 매핑됨.
     * >> /image 이하의 url이 이미지 저장경로를 의미
     */
    @GetMapping("/**")
    public ResponseEntity<Resource> getImage( HttpServletRequest request ) throws MalformedURLException {

        String requestUrl = request.getRequestURI();
        // TODO 아래 데이터 차이 확인 필요
        log.info( request.getPathInfo() );
        log.info( request.getRequestURI() );
        log.info( request.getRequestURL().toString() );
        String imageSaveUrl = requestUrl.replace( "/image", "" );
        return ResponseEntity.ok( provider.provide( imageSaveUrl ) );
    }

}
