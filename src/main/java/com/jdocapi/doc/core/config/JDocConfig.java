package com.jdocapi.doc.core.config;

import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import com.jdocapi.doc.utils.PropUtils;

public class JDocConfig {

    private static JDocConfig singleJDocConfig = null;

    private static Properties properties;

    public static JDocConfig instance(String configFileName) {
        if (singleJDocConfig == null) {
            singleJDocConfig = new JDocConfig();
            properties = PropUtils.builderProperties(configFileName);
        }
        return singleJDocConfig;
    };

    public static synchronized String getValue(String key) {
        String value = properties.getProperty(key);
        if (StringUtils.isNotBlank(value)) {
            value = value.trim();
        } else {
            return "";
        }
        return value;
    }

}
