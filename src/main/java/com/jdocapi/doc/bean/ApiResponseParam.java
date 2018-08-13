package com.jdocapi.doc.bean;

import java.util.List;

/**
 * 
 * @author haoting.wang
 *
 */
public class ApiResponseParam {

    /**
     * 参数名称
     */
    private String name;

    /**
     * 是否必传
     */

    private String required;

    /**
     * 参数描述
     */
    private String desc;

    /**
     * 请求类型
     */
    private String type;

    private List<ApiResponseParam> subApiResponseParam;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ApiResponseParam> getSubApiResponseParam() {
        return subApiResponseParam;
    }

    public void setSubApiResponseParam(List<ApiResponseParam> subApiResponseParam) {
        this.subApiResponseParam = subApiResponseParam;
    }
}
