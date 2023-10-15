package com.threeNerds.basketballDiary.file;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


@Component
@RequiredArgsConstructor
public class ExcelUploader implements Uploader {

    private final PathManager excelPathManger;

    @Override
    public String upload( String savePath, MultipartFile input ) {
        // TODO 엑셀 업로드 구현하기
        return "";
    }

}
