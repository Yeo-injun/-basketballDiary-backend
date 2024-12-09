package com.threeNerds.basketballDiary.file;

import org.springframework.web.multipart.MultipartFile;


public interface Uploader <T> {

    String upload( T savePath, MultipartFile input );

}
