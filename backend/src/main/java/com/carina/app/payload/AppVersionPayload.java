package com.carina.app.payload;

public class AppVersionPayload {

    private String version;
    private String app;

    public AppVersionPayload(String app, String version) {
        this.version = version;
        this.app = app;
    }

    public String getVersion() {
        return this.version;
    }

    public String getApp() {
        return this.app;
    }

}
