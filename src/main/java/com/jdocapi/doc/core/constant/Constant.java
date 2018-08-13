package com.jdocapi.doc.core.constant;

import com.jdocapi.doc.core.config.JDocConfig;

public class Constant {

    public static final String DEFAULT_CHARSET = "utf-8";

    public static final String JFINAL = "jfinal";

    public static final String POST = "post";

    public static final String GET = "get";

    public static final String HTML_TEMPALTE = "jdoc_template.ftl";
    
    public static final String WORD_TEMPALTE = "word_template.ftl";


    public static final String SPRING_MVC = "springmvc";

    public static final String SERVICE_INTERFACE = "service";

    public static final String SPRING_BOOT = "springboot";

    public static final String JAVA_FILE_SUFFIX = ".java";

    public static final String HTML_FORMATER = "html";
    
    public static final String WORD_FORMATER = "word";

    public static final String HTTP_STATUS_CODE = "status";

    public static final String JAVA_FILE_PATH = (System.getProperty("user.dir") + "/" + JDocConfig.getValue("java.source") + "/");


    /**
     * 字节参数类型
     */
    public static final String BYTE = "Byte";

    /**
     * 断整形参数类型
     */
    public static final String SHORT = "Short";
    /**
     * 整数参数类型
     */
    public static final String INTEGER = "Integer";

    /**
     * 长整数参数类型
     */
    public static final String LONG = "Long";

    /**
     * 字符串参数类型
     */
    public static final String CHAR = "Char";
    /**
     * 字符串参数类型
     */
    public static final String STRING = "String";

    /**
     * 布尔类型
     */
    public static final String BOOLEAN = "Boolean";

    /**
     * 浮点数类型
     */
    public static final String FLOAT = "Float";
    /**
     * 浮点数类型
     */
    public static final String DOUBLE = "Double";
    
    /**
     * 时间类型
     */
    public static final String DATE = "Date";
    
    /**
     * 请求为json类型
     */
    public static final String REQUEST_TYPE_JSON = "json";
}
