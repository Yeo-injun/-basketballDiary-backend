package com.threeNerds.basketballDiary.file;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class ImageUploader implements Uploader<ImageUploader.Path> {

    private final PathManager imagePathManager;

    @Override
    public String upload( Path path, MultipartFile input ) {
        if ( null == input ) {
            return ""; // 이미지가 없으면 경로를 ""로 return
        }
        // TODO 이름 중복 체크
        // TODO 최대 사이즈 체크
        String uploadName = input.getOriginalFilename();
        File targetFile = new File( imagePathManager.makeDir( getUploadFullPath( path ), PathManager.Type.IMAGE ), uploadName );
        try {
            // 이미지 물리적 저장 완료
            input.transferTo( targetFile );
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 이미지 저장위치 리턴 : URL로
        return imagePathManager.toURL( targetFile, PathManager.Type.IMAGE );
    }

    /**
     * 이미지 저장 목적지 경로 생성
     * - path/yyyyMMdd 형태의 경로 return
     */
    private String getUploadFullPath( Path path ) {
        return  Optional.ofNullable( path.getImagePath() ).orElseGet( ()-> "" )
                + "/"
                + LocalDate
                    .now()
                    .format( DateTimeFormatter.ofPattern( "yyyy/MM/dd" ) )
                    .replace( "/", "" );
    }

    /**
     * 이미지 Path관리
     */
    @Getter
    public enum Path {
        PROFILE_THUMBNAIL( "소속팀 프로필", "/myTeam/profile" ),
        TEAM_LOGO( "팀로고", "/myTeam/logo" );

        private String imageName;
        private String imagePath;

        Path( String imageName, String imagePath ) {
            this.imageName = imageName;
            this.imagePath = imagePath;
        }
    }
}
