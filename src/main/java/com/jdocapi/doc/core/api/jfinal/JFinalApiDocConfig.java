package com.jdocapi.doc.core.api.jfinal;

import java.util.HashMap;
import java.util.Map;

import com.jdocapi.doc.core.JDocCore;
import com.jdocapi.doc.core.api.ApiDocConfig;
import com.jdocapi.doc.core.exception.JDocException;
import com.jfinal.core.Controller;

/**
 * JFinal获取配置类方法
 * 
 * @author haoting.wang
 */
public class JFinalApiDocConfig implements ApiDocConfig {

    private static Map<String, String> controllerKeys = null;

    private static Boolean             isUseClearSuffix;

    private static String              clearSuffix;

    private String                     configFilePath;

    public JFinalApiDocConfig(String configFilePath){
        this.configFilePath = configFilePath;
    }

    @Override
    public void start() {
        if (isUseClearSuffix == null) {
            isUseClearSuffix = true;
        }
        new JDocCore().setConfigFileName(configFilePath).builder();
    }

    public JFinalApiDocConfig add(String key, Class<? extends Controller> controllerClass) {
        if (controllerKeys == null) {
            controllerKeys = new HashMap<String, String>();
        }
        controllerKeys.put(controllerClass.getSimpleName(), key);
        return this;
    }

    public JFinalApiDocConfig setUseClearSuffix(boolean isUseClearSuffix) {
        JFinalApiDocConfig.isUseClearSuffix = isUseClearSuffix;
        return this;

    }

    public JFinalApiDocConfig setClearSuffix(String clearSuffix) {
        JFinalApiDocConfig.clearSuffix = clearSuffix;
        return this;
    }

    /**
     * 获取根url
     * 
     * @return
     */
    public static String getControllerKey(String name) {

        if (isUseClearSuffix) {
            return "/" + name.replaceAll(clearSuffix, "").toLowerCase();
        }

        if (controllerKeys == null) {
            throw new JDocException("please add routes on JFinalApiDocConfig");
        }
        return controllerKeys.get(name);
    }

}
