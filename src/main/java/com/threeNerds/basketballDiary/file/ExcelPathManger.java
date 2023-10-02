package com.threeNerds.basketballDiary.file;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ExcelPathManger implements PathManager {


    @Override
    public File makeDir(String dir) {
        return null;
    }

    @Override
    public String toURL(File file) {
        return null;
    }

    @Override
    public String toPath(String url) {
        return null;
    }
}
