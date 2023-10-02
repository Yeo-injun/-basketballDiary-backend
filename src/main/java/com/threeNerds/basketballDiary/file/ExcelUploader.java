package com.threeNerds.basketballDiary.file;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


@Component
@RequiredArgsConstructor
public class ExcelUploader implements Uploader {

    private final PathManager pathManager;

    @Override
    public String upload( File saveUrl, MultipartFile input ) {
        // TODO 엑셀 업로드 구현하기
        return "";
    }

}
