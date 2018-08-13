package com.jdocapi.doc.bean;

import java.util.List;

public class Param {

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

    private List<Param> params;

    private boolean simpleType;

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

    public List<Param> getParams() {
        return params;
    }

    public void setParams(List<Param> params) {
        this.params = params;
    }
}
