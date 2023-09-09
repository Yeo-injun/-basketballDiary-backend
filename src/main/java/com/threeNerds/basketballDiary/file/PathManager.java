package com.threeNerds.basketballDiary.file;

import java.io.File;

public interface PathManager {

    File makeDir( String dir );

    String toURL( File file );

    String toPath( String url );

}
