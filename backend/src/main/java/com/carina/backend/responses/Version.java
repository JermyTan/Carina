package com.carina.backend.responses;

public class Version {

    private String versionNumber;

    public Version(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getVersion() {
        return versionNumber;
    }

}
