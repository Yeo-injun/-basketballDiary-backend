package com.threeNerds.basketballDiary.file;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URI;


@Component
@RequiredArgsConstructor
public class ImageUploader implements Uploader {

    private final PathManager pathManager;

    @Override
    public String upload( File saveUrl, MultipartFile input ) {
        // TODO 이름 중복 체크
        // TODO 최대 사이즈 체크
        String uploadName = input.getOriginalFilename();
        File targetFile = new File( saveUrl, uploadName );
        try {
            // 이미지 물리적 저장 완료
            input.transferTo( targetFile );
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 이미지 저장위치 리턴 : URL로
        return pathManager.toURL( targetFile );
    }

}
