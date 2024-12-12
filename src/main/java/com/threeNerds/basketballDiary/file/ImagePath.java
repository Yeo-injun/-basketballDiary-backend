package com.threeNerds.basketballDiary.file;


import lombok.Getter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Getter
@Component
public class ImagePath {

    /**
     * 생성자 주입방식으로 Bean을 초기화시킴.
     * - Bean으로 등록시 생성자 주입을 통해 static 필드에 설정파일의 값을 주입
     */
    public ImagePath(
        @Value("${file.image.path.team-profile}") String PATH_TEAM_PROFILE,
        @Value("${file.image.path.team-logo}") String PATH_TEAM_LOGO
    ) {
        ImagePath.PATH_TEAM_PROFILE = PATH_TEAM_PROFILE;
        ImagePath.PATH_TEAM_LOGO    = PATH_TEAM_LOGO;
    }

    private static String PATH_TEAM_PROFILE;
    private static String PATH_TEAM_LOGO;

    /**
     * Inner Class는 Outer Class의 private 필드에 접근할 수 있다.
     * 이에 따라 EnumType 초기화시 Outer Class의 private 필드를 참조하여 Enum이 초기화되도록 한다.
     *
     * 다만, InnerClass의 EnumType이 접근하고자 하는 필드는 static해야 한다.
     * 왜냐하면 EnumType은 객체 생성없이 OuterClass를 정적으로 참조하여 EnumType에 접근할 수 있기 때문이다.
     *
     * EnumType에 최초 접근시 EnumTyoe이 초기화되기 때문에
     * OuterClass의 static 필드는 EnumType 참조 전에 주입되어야 EnumType이 초기화시 정상적으로 해당 값을 반영한다.
     **/
    public enum Type implements PathType {
        TEAM_PROFILE( PATH_TEAM_PROFILE ),
        TEAM_LOGO( PATH_TEAM_LOGO );
        private final String path;
        Type( String path ) {
            this.path           = path;
        }

        @Override
        public String getFullPath() {
            return this.path + "/" + LocalDate.now().format( DateTimeFormatter.ofPattern( "yyyyMMdd" ) );
        }
    }

}
