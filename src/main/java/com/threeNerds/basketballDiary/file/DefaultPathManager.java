package com.threeNerds.basketballDiary.file;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class DefaultPathManager implements PathManager {

    @Value("${file.upload.root.image}")
    private String imageRootPath;

    private String excelRootPath;



    @Override
    public File makeDir( String path, Type fileType ) {
        // 저장경로 설정
        File targetPath = new File( getRootPath( fileType ), path );

        // 저장경로 물리적 생성
        targetPath.mkdirs();

        // 저장경로를 가지고 있는 File객체 리턴
        return targetPath;
    }

    private String getRootPath( Type fileType ) {
        switch ( fileType ) {
            case IMAGE : return imageRootPath;
            case EXCEL : return excelRootPath;
        }
        return "";
    }
    /**
     * url을 받아서 파일이 저장된 물리적 위치를 반환함
     * @param url
     * @return
     */
    @Override
    public String toPath( String url, Type fileType ) {
        return getRootPath( fileType ) + url;
    }

    /**
     * 파일이 저장된 물리적 위치를 URL의 Path부분 반환
     * - file의 root를 제거하고, URL패턴으로 제공
     * - 운영체제별로 파일시스템의 경로 구분자가 다르기 때문에 URL로 제공하기!!
     */
    @Override
    public String toURL( File file, Type fileType ) {
        String fileURL = file.toURI().getPath();
        String rootURL = new File( getRootPath( fileType ) ).toURI().getPath();
        return fileURL.replace( rootURL , "/" );
    }

}
