package com.threeNerds.basketballDiary.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class ImagePathManager implements PathManager {

    @Value("${file.upload.root.image}")
    private String defaultPath;

    @Override
    public File makeDir( String path ) {
        // 저장경로 설정
        File targetPath = new File( defaultPath, path );

        // 저장경로 물리적 생성
        targetPath.mkdirs();
        
        // 저장경로를 가지고 있는 File객체 리턴
        return targetPath;
    }

    public File makeDirWithYmdPattern( String path ) {
        String childPath = path + "/" + getPathNameWithYmdPattern();
        return makeDir( childPath );
    }

    private String getPathNameWithYmdPattern() {
        return LocalDate.now().format( DateTimeFormatter.ofPattern( "yyyy/MM/dd" ) ).replace( "/", "" );
    }


}
