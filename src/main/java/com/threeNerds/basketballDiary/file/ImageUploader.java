package com.threeNerds.basketballDiary.file;

import com.threeNerds.basketballDiary.file.exception.ExceedMaxFileSizeException;
import com.threeNerds.basketballDiary.file.exception.NotAllowedFileExtensionException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Stream;


@Component
@RequiredArgsConstructor
@Slf4j
public class ImageUploader implements Uploader<ImageUploader.Path> {

    private static final Set<String> allowedExtensions = new HashSet<>( List.of( "png", "jpeg", "jpg", "gif", "svg" ) );
    private static final long MAX_SIZE_IN_BYTES = 1024 * 1024 * 3 / 2; // 1.5Mb로 반영. yml파일의 Multipart max-size보다 작게 반영 ( bytes > Kb > Mb > Gb > Tb ... )

    private final ImagePathManager imagePathManager;

    /**
     * 이미지 Path관리
     * TODO 이미지 PathManger 구현을 통해 해당 내용 관리 + 해당 내용을 설정값으로 관리
     */
    @Getter
    public enum Path {
        PROFILE_THUMBNAIL( "소속팀 프로필", "/myTeam/profile" ),
        TEAM_LOGO( "팀로고", "/myTeam/logo" );

        private final String imageName;
        private final String imagePath;

        Path( String imageName, String imagePath ) {
            this.imageName = imageName;
            this.imagePath = imagePath;
        }
        //        PROFILE_THUMBNAIL( "소속팀 프로필", "/myTeam/profile" ),
//        TEAM_LOGO( "팀로고", "/myTeam/logo" );
//
//        private final String imageName;
//        private final String imagePath;
//
//        Path( String imageName, String imagePath ) {
//            this.imageName = imageName;
//            this.imagePath = imagePath;
//        }
    }

    @Override
    public String upload( Path path, MultipartFile input ) {
        if ( null == input || input.isEmpty() ) {
            return ""; // 이미지가 없으면 경로를 ""로 return
        }

        String fileExtension = getFileExtenstion( input.getOriginalFilename() );
        if ( !isAllowedExtenstion( fileExtension ) ) {
            throw new NotAllowedFileExtensionException();
        }

        if ( exceedMaxSizeInBytes( input ) ) {
            throw new ExceedMaxFileSizeException();
        }

        File uploadPath = imagePathManager.makeDir( getUploadPath( path ) );
        File targetFile = new File( uploadPath, getUniqueFileName( fileExtension ) );
        try {
            // 이미지 물리적 저장
            input.transferTo( targetFile );
        } catch ( IOException e ) {
            log.warn( "[ Fail To Save Image ]" );
            e.printStackTrace();
        }

        // 이미지 저장위치 리턴 : URL로
        return imagePathManager.toURL( targetFile );
    }

    /**
     * image파일의 bytes사이즈가 최대Size를 초과하는지 검사하는 메소드
     */
    private boolean exceedMaxSizeInBytes( MultipartFile imageFile ) {
        return MAX_SIZE_IN_BYTES < imageFile.getSize();
    }

    /**
     * 확장자 추출
     */
    private String getFileExtenstion( String fileName ) {
       int extensionSeparatorIdx = fileName.lastIndexOf( "." );
       boolean hasExtension = extensionSeparatorIdx > 0;
       if ( hasExtension ) {
           return fileName.substring( extensionSeparatorIdx + 1 );
       }
       return "";
    }

    /**
     * 업로드 가능한 이미지 파일 확장자
     */
    private boolean isAllowedExtenstion( String fileExtension ) {
        if ( "".equals( fileExtension ) ) {
            return false;
        }
        return allowedExtensions.contains( fileExtension );
    }

    /**
     * 이미지 저장 목적지 경로 생성
     * - path/yyyyMMdd 형태의 경로 return
     */
    // TODO Type 이넘을 없애고 업로드 클래스별 PathManager를 별도 구현하기.
    // PathManager내에서 Path생성로직을 관리.
    @Deprecated // TODO 해당 메소드를 PathManager에서 파일 Type에 따라 처리할 수 있도록 구현변경..!
    private String getUploadPath( Path path ) {
        return  Optional.ofNullable( path.getImagePath() ).orElseGet( ()-> "" )
                + "/"
                + LocalDate
                    .now()
                    .format( DateTimeFormatter.ofPattern( "yyyy/MM/dd" ) )
                    .replace( "/", "" );
    }

    /**
     * Unique 파일명 생성 unique
     */
    private String getUniqueFileName( String extension ) {
        // UUID로 채번
        /** feat. GPT
         * UUID(Universally Unique Identifier)는 전 세계적으로 고유한 값을 생성하기 위한 식별자입니다.
         * 1. 고유성: UUID는 매우 높은 수준의 고유성을 제공합니다. 무작위로 생성되거나 특정 규칙에 따라 생성되기 때문에 충돌 가능성이 극히 낮습니다.
         * 2. 분산 시스템에서의 사용: 여러 시스템이나 서비스 간에 고유한 식별자가 필요한 경우가 있습니다. UUID는 중앙 권한이나 중앙화된 서비스 없이도 고유성을 보장하므로, 분산 시스템에서 특히 유용합니다.
         * 3. 데이터베이스에서의 사용: 데이터베이스에서 레코드를 고유하게 식별하는 데 사용됩니다. 특히 데이터를 여러 서버 또는 여러 데이터베이스 간에 이동할 때 충돌이 발생하지 않도록 보장합니다.
         * 4. 보안: 무작위로 생성되는 UUID는 예측하기 어려우며, 이는 보안적인 측면에서 유용합니다. 예측 가능성이 없기 때문에 다른 자격 증명을 쉽게 대체할 수 없습니다.
         * 5. 실시간 시스템에서의 성능: 일부 실시간 시스템에서는 순차적인 식별자가 아닌 무작위로 생성된 UUID를 사용하여 성능을 향상시킬 수 있습니다. 순차적인 식별자는 여러 스레드 또는 서버 간에 병목 현상을 일으킬 수 있지만, UUID는 이러한 문제를 완화할 수 있습니다.
         * 6. 이동성: 데이터나 객체의 이동성이 중요한 경우 UUID를 사용하면 해당 객체가 어디에 있든 고유한 식별자를 유지할 수 있습니다.
         **/
        String uniqueFileName = UUID.randomUUID().toString().replace( "-", "" );
        return uniqueFileName + "." + extension;
    }

}
