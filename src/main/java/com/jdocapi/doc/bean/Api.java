package com.jdocapi.doc.bean;

import java.util.List;

/**
 * 业务模块
 * 
 * @author haoting.wang
 */
public class Api {

    /**
     * 描述
     */
    private String          title;

    /**
     * 业务具体实现接口
     */
    private List<ApiAction> apiActions;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ApiAction> getApiActions() {
        return apiActions;
    }

    public void setApiActions(List<ApiAction> apiActions) {
        this.apiActions = apiActions;
    }

}
