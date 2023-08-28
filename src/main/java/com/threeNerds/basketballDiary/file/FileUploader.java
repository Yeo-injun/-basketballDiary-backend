package com.threeNerds.basketballDiary.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface FileUploader {

    String upload( File destFolder, MultipartFile input );

}
