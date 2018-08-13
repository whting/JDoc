package com.jdocapi.doc.bean;

import java.util.List;

/**
 * 接口描述
 * 
 * @author haoting.wang
 */
public class ApiAction {

    /**
     * 接口标题
     */
    private String                 title;

    /**
     * 接口描述
     */
    private String                 desc;

    /**
     * 请求URL
     */
    private String                 url;

    /**
     * 方法签名
     */
    private String                 methodSign;

    /**
     * 版本
     */
    private String                 version;

    /**
     * 作者
     */
    private String                 author;

    /**
     * 接口请求时间
     */
    private long                   requestTime;
    /**
     * 请求类型 post,get
     */
    private String                 requestType;

    /**
     * 请求参数
     */
    private List<ApiRequestParam>  reqParams;

    /**
     * 返回参数
     */
    private List<ApiResponseParam> respParams;

    /**
     * 请求返回样本
     */
    private String                 respText;

    /**
     * 返回描述
     */
    private String                 returnDesc;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<ApiRequestParam> getReqParams() {
        return reqParams;
    }

    public void setReqParams(List<ApiRequestParam> reqParams) {
        this.reqParams = reqParams;
    }

    public List<ApiResponseParam> getRespParams() {
        return respParams;
    }

    public void setRespParams(List<ApiResponseParam> respParams) {
        this.respParams = respParams;
    }

    public String getRespText() {
        return respText;
    }

    public void setRespText(String respText) {
        this.respText = respText;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethodSign() {
        return methodSign;
    }

    public void setMethodSign(String methodSign) {
        this.methodSign = methodSign;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public long getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(long requestTime) {
        this.requestTime = requestTime;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getReturnDesc() {
        return returnDesc;
    }

    public void setReturnDesc(String returnDesc) {
        this.returnDesc = returnDesc;
    }
}
