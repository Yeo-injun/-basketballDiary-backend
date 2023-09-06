package com.threeNerds.basketballDiary.file;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.MalformedURLException;

public interface Provider {

    Resource provide( String path ) throws MalformedURLException;

}
