package com.threeNerds.basketballDiary.mvc.controller.Test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {

    // sendRedirect TEST
    @GetMapping("/sendRedirect")
    public ResponseEntity<?> test(
            @RequestParam String redirectURL
    )
    {
        log.info("#########################################");
        return ResponseEntity.ok("테스트입니다. : " + redirectURL);
    }
}
