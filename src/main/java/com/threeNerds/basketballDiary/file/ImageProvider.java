package com.threeNerds.basketballDiary.file;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
public class ImageProvider implements Uploader {
    // TODO yml설정에 따라 기본 경로가 바뀌게끔 처리하기 ( 서버에 따라 바뀜 )
    final private String defaultPath = "/image/upload/";

    @Override
    public String upload( File destFolder, MultipartFile input ) {
        // TODO 이름 중복 체크
        // TODO 최대 사이즈 체크
        String uploadName = input.getOriginalFilename();
        File targetFile = new File( destFolder, uploadName );
        try {
            input.transferTo( targetFile );
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 이미지 저장위치 리턴 TODO
        return targetFile.toURI().getPath();
    }

}
