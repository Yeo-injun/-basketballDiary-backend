package com.threeNerds.basketballDiary.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.URISyntaxException;

public interface Uploader {

    String upload( String savePath, MultipartFile input );

}
