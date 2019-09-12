package com.carina.backend.controllers;

import com.carina.backend.responses.Version;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersionController {

    @GetMapping("/version")
    Version getVersion() {
        return new Version("v0.1");
    }

}
