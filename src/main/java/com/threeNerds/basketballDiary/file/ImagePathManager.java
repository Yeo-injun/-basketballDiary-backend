package com.threeNerds.basketballDiary.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Supplier;

@Component
public class ImagePathManager implements PathManager {

    @Value("${file.image.path.root}")
    private String rootPath;
    @Value("${file.image.path.teamProfile}")
    private String PATH_TEAM_PROFILE;
    @Value("${file.image.path.teamLogo}")
    private String PATH_TEAM_LOGO;

    public File makeDir( PathType pathType ) {
        // 저장경로 설정
        File targetPath = new File( getFullPath( pathType ) );

        // 저장경로 물리적 생성
        targetPath.mkdirs();

        // 저장경로를 가지고 있는 File객체 리턴
        return targetPath;
    }

    private String getFullPath( PathType pathType ) {
        ImagePath path = (ImagePath) pathType;
        String subPath = "";
        switch ( path ) {
        case TEAM_PROFILE    : subPath = this.PATH_TEAM_PROFILE; break;
        case TEAM_LOGO       : subPath = this.PATH_TEAM_LOGO; break;
        default:
            subPath = "";
        }
        return this.rootPath + subPath + "/" + LocalDate.now().format( DateTimeFormatter.ofPattern( "yyyyMMdd" ) );
    }

    /**
     * url을 받아서 파일이 저장된 물리적 위치를 반환함
     * @param url
     * @return
     */
    @Override
    public String toPath( String url ) {
        return this.rootPath + url;
    }

    /**
     * 파일이 저장된 물리적 위치를 URL의 Path부분 반환
     * - file의 root를 제거하고, URL패턴으로 제공
     * - 운영체제별로 파일시스템의 경로 구분자가 다르기 때문에 URL로 제공하기!!
     */
    @Override
    public String toURL( File file ) {
        String fileURL = file.toURI().getPath();
        String rootURL = new File( this.rootPath ).toURI().getPath();
        return fileURL.replace( rootURL , "/" );
    }

}
