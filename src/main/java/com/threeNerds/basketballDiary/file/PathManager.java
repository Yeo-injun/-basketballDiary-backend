package com.threeNerds.basketballDiary.file;

import java.io.File;

public interface PathManager {
    enum Type {
        IMAGE( "image" ),
        EXCEL( "excel" );

        private final String fileType;

        Type( String fileType ) {
            this.fileType = fileType;
        }
    }

    File makeDir( String dir, Type fileType );

    String toURL( File file, Type fileType );

    String toPath( String url, Type fileType );

}
