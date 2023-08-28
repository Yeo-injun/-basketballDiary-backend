package com.threeNerds.basketballDiary.file;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class ImageUploader implements FileUploader, FilePathManager {
    // TODO yml설정에 따라 기본 경로가 바뀌게끔 처리하기 ( 서버에 따라 바뀜 )
    final private String defaultPath = "D:\\upload/";

    /**
     * 파일을 저장할 경로를 미리 생성
     * ex) 오늘이 2022/06/18 이라면 D드라이브 upload 폴더에 2022->06->18 이라는 폴더들이 계층별로 생성됨
     */
    @Override
    public File makeUploadPath() {
        File folder = new File( defaultPath, makePathWithDatePattern() );
        if ( folder.mkdirs() ) {
            return folder;
        }
        // TODO 에러 던지기 ( 시스템 에러 )
        return null;
    }

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
        // 이미지가 저장위치 리턴 TODO
        return targetFile.toURI().getPath();
    }

    // 해당 경로의 패턴은 서버가 바뀌어도 동일함.
    private String makePathWithDatePattern() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")).replace("/", File.separator);
    }

}
