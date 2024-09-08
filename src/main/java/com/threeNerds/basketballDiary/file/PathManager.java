package com.threeNerds.basketballDiary.file;

import java.io.File;

public interface PathManager {

    File makeDir( PathType pathType );

    String toURL( File file );

    String toPath( String url );

}
