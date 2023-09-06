package com.threeNerds.basketballDiary.file;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class ImagePathManager implements PathManager {

    @Value("${file.upload.root.image}")
    private String root;

    @Override
    public File makeDir( String path ) {
        // 저장경로 설정
        File targetPath = new File( root, path );

        // 저장경로 물리적 생성
        targetPath.mkdirs();
        
        // 저장경로를 가지고 있는 File객체 리턴
        return targetPath;
    }

    @Override
    public String getRootDir() {
        return this.root;
    }

    /**
     * 전체경로에서 root경로 제거
     * @param fullPath
     * @return root경로가 제거된 Path
     */
    @Override
    public String removeRootDir( String fullPath ) {
        File rootDir = new File( this.root );
        String rootPath = rootDir.getPath();
        // TODO 이미지 Root 경로 제거하는 로직 수정 필요
        return fullPath.replace( "/D:/image", "" );
    }

    public File makeDirWithYmdPattern( String path ) {
        String childPath = path + "/" + getPathNameWithYmdPattern();
        return makeDir( childPath );
    }

    private String getPathNameWithYmdPattern() {
        return LocalDate.now().format( DateTimeFormatter.ofPattern( "yyyy/MM/dd" ) ).replace( "/", "" );
    }


}
