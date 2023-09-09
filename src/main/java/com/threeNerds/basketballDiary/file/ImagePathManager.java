package com.threeNerds.basketballDiary.file;

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

    /**
     * url을 받아서 파일이 저장된 물리적 위치를 반환함
     * @param url
     * @return
     */
    @Override
    public String toPath( String url ) {
        return "file:" + this.root + url;
    }

    /**
     * 파일이 저장된 물리적 위치를 URL로 반환
     * - file의 root를 제거하고, URL패턴으로 제공
     * - 운영체제별로 파일시스템의 dir구분자가 다르기 때문에 URL로 제공하기!!
     * TODO Uniform Resource Location의 의미는 통합된 자원의 위치.
     * 자원의 위치를 나타내는 기호가 각기 다른 운영체제마다 다르기 때문에 통일성있는 체계가 필요했다고 추측됨.
     * @param file
     * @return
     */
    @Override
    public String toURL( File file ) {
        String fileURL = file.toURI().getPath();
        String rootURL = new File( this.root ).toURI().getPath();
        return fileURL.replace( rootURL , "/" );
    }

    public File makeDirWithYmdPattern( String path ) {
        String childPath = path + "/" + getPathNameWithYmdPattern();
        return makeDir( childPath );
    }

    private String getPathNameWithYmdPattern() {
        return LocalDate.now().format( DateTimeFormatter.ofPattern( "yyyy/MM/dd" ) ).replace( "/", "" );
    }


}
