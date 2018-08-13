package com.jdocapi.doc.core.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.jdocapi.doc.bean.ApiRequestParam;
import com.jdocapi.doc.bean.ApiResponseParam;
import com.jdocapi.doc.bean.Param;

/**
 * param容器，用于存储see注释的bean
 * 
 * @author haoting.wang
 */
public class BeanParamContainer {

    public static ConcurrentHashMap<String, List<Param>> beanParams = new ConcurrentHashMap<String, List<Param>>();

    public static List<Param> getParam(String beanName) {
        return beanParams.get(beanName);
    }

    public static void putBeanParam(List<Param> params, String beanName) {
        beanParams.put(beanName, params);
    }

    public static List<ApiRequestParam> getApiRequestParamParam(String beanName) {
        List<Param> params = beanParams.get(beanName);
        if (params == null) {
            return null;
        }

        List<ApiRequestParam> apiRequestParams = new ArrayList<ApiRequestParam>();

        for (Param param : params) {
            ApiRequestParam apiRequestParam = new ApiRequestParam();
            apiRequestParam.setDesc(param.getDesc());
            apiRequestParam.setName(param.getName());
            apiRequestParam.setRequired(param.getRequired());
            apiRequestParam.setType(param.getType());
            apiRequestParams.add(apiRequestParam);
        }
        return apiRequestParams;
    }

    public static List<ApiResponseParam> getApiResponseParam(String beanName) {
        List<Param> params = beanParams.get(beanName);
        if (params == null) {
            return null;
        }

        return param2ApiResponseParam(params);

    }

    public static List<ApiResponseParam> param2ApiResponseParam(List<Param> params) {
        List<ApiResponseParam> apiResponseParams = new ArrayList();
        for (Param param : params) {
            ApiResponseParam apiResponseParam = new ApiResponseParam();
            apiResponseParam.setDesc(param.getDesc());
            apiResponseParam.setName(param.getName());
            apiResponseParam.setRequired(param.getRequired());
            apiResponseParam.setType(param.getType());
            if (param.getParams() != null && !param.getParams().isEmpty()) {
                apiResponseParam.setSubApiResponseParam(param2ApiResponseParam(param.getParams()));

            }
            apiResponseParams.add(apiResponseParam);
        }

        return apiResponseParams;
    }
}
