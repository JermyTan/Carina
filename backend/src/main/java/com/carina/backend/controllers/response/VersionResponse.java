package com.carina.backend.controllers.response;

import com.carina.backend.services.Version;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersionResponse {

    @GetMapping("/version")
    Version getVersion() {
        return new Version("v0.1");
    }

}
