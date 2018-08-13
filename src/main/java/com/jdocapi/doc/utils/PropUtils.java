package com.jdocapi.doc.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import com.jdocapi.doc.core.constant.Constant;

public class PropUtils {

  
    public static Properties builderProperties(String fileName) {
        return builderProperties(fileName, Constant.DEFAULT_CHARSET);
    }

    public static Properties builderProperties(String fileName, String encoding) {
        Properties properties = null;
        InputStream inputStream = null;
        try {
            inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if (inputStream == null)
                throw new IllegalArgumentException("Properties file not found in classpath: " + fileName);
            properties = new Properties();
            properties.load(new InputStreamReader(inputStream, encoding));
        } catch (IOException e) {
            throw new RuntimeException("Error loading properties file.", e);
        } finally {
            if (inputStream != null)
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return properties;
    }  
}
