package com.jdocapi.doc.core.api.service;


import com.jdocapi.doc.core.JDocCore;
import com.jdocapi.doc.core.api.ApiDocConfig;

public class ServiceApiDocConfig implements ApiDocConfig {

    private String configFilePath;

    @Override
    public void start() {
        new JDocCore().setConfigFileName(configFilePath).builder();
    }

    public String getConfigFilePath() {
        return configFilePath;
    }

    public void setConfigFilePath(String configFilePath) {
        this.configFilePath = configFilePath;
    }

}
