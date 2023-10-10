package com.threeNerds.basketballDiary.file;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * 이미지 불러오기 위한 클래스
 * - 처리 방법
 *      1. 저장된 이미지를 FileInputStream클래스로 가져오고 Byte Array로 변환해서 return / URL : https://redbinalgorithm.tistory.com/382
 *      2. Spring boot의 ResourceHandlers메소드를 구현하여 ResourceHandlerRegistry에 이미지 저장 경로를 반영해주기
 *          >> 하지만 이 방법은 thymeleaf에서 사용하는 방법. Thymeleaf는 SSR로 WAS에서 HTML를 만들어서 리턴해주기 때문에 가능한 방법으로 판단됨. CRS방식이기 때문에 해당 방법으로는 이미지를 제대로 리턴해줄 수 없을것으로 보임.
 *          참고 URL : https://mopil.tistory.com/58
 *      3. URL 리소스 방식
 *          참고자료 : https://velog.io/@gmtmoney2357/%EC%8A%A4%ED%94%84%EB%A7%81-%EB%B6%80%ED%8A%B8-%ED%8C%8C%EC%9D%BC-%EC%97%85%EB%A1%9C%EB%93%9C-%EB%8B%A4%EC%9A%B4%EB%A1%9C%EB%93%9C-%EC%9D%B4%EB%AF%B8%EC%A7%80-%EB%B3%B4%EC%97%AC%EC%A3%BC%EA%B8%B0
 *
 */
@Component
@RequiredArgsConstructor
public class ImageProvider implements Provider {

    private final PathManager imagePathManager;

    @Override
    public Resource provide( String saveUrl ) throws MalformedURLException {
        // TODO 작동방식 확인하기
        // 로컬 테스트 결과 - 성공
        // 개발서버 테스트 결과 -
        return new FileSystemResource( imagePathManager.toPath( saveUrl, PathManager.Type.IMAGE ) );
    }

}
