package com.carina.app.controller;

import com.carina.app.payload.AppVersionPayload;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class AppVersionController {

    @GetMapping("")
    AppVersionPayload getAppVersion() {
        return new AppVersionPayload("Carina", "v0.1");
    }

}
