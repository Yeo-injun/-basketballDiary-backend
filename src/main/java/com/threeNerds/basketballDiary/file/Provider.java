package com.threeNerds.basketballDiary.file;

import org.springframework.core.io.Resource;

import java.net.MalformedURLException;

public interface Provider {

    Resource provide( String path ) throws MalformedURLException;

}
