package com.jdocapi.doc.core.analysis;

import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.jdocapi.doc.bean.Api;
import com.jdocapi.doc.bean.ApiAction;
import com.jdocapi.doc.bean.ApiRequestParam;
import com.jdocapi.doc.core.config.JDocConfig;
import com.jdocapi.doc.core.constant.Constant;
import com.jdocapi.doc.core.exception.JDocException;
import com.jdocapi.doc.core.formater.Formater;
import com.jdocapi.doc.core.formater.FormaterBuilder;
import com.jdocapi.doc.utils.DataUtils;
import com.jdocapi.doc.utils.HttpUtils;

/**
 * 接口文档分析器
 * 
 * @author haoting.wang
 */
public class ApiAnalysis extends Thread {

    private static final String DEFAULT_PREFIX_URL = "http://localhost:8080";

    private List<Api>           apis;

    public ApiAnalysis(List<Api> apis){
        this.apis = apis;
    }

    public void analysisApi() {

        if (apis == null || apis.size() <= 0) return;

        String paramLengthStr = JDocConfig.getValue("analysis.param.maxlength");

        String prefixUrl = JDocConfig.getValue("analysis.prefix.url");

        int paramLength = 0;

        if (StringUtils.isBlank(prefixUrl)) {
            prefixUrl = DEFAULT_PREFIX_URL;
        }
        if (StringUtils.isNotBlank(paramLengthStr)) {
            paramLength = Integer.parseInt(paramLengthStr);
        }

        for (Api api : apis) {
            List<ApiAction> apiActions = api.getApiActions();
            if (apiActions == null || apiActions.size() <= 0) {
                continue;
            }

            for (ApiAction apiAction : apiActions) {
                String url = prefixUrl + apiAction.getUrl();

                Map<String, String> requestParams = null;
                if (paramLength == 0) {
                    requestParams = builderStandardRequestParam(apiAction.getReqParams());
                } else {
                    requestParams = builderMaxRequestParam(apiAction.getReqParams(), paramLength);
                }
                long startTime = System.currentTimeMillis();
                try {
                    if (StringUtils.contains(apiAction.getRequestType(), Constant.POST)) {
                        HttpUtils.sendPost(url, requestParams, 10000);
                    } else {
                        HttpUtils.sendGet(url, requestParams, 10000);
                    }
                } catch (SocketTimeoutException e) {
                    throw new JDocException("url:" + apiAction.getUrl() + ";reqeust timeout:" + 10000 / 1000
                                            + "second");
                }

                long endTime = System.currentTimeMillis();
                apiAction.setRequestTime(endTime - startTime);

            }

            Formater formater = new FormaterBuilder().builder();
            formater.output(apis);

        }
    }

    /**
     * 生成标准的请求参数,数字长度为1,字符串长度为6,时间标准格式为"2017-02-18",布尔类型为true或是false
     * 
     * @param apiRequstParam
     * @return
     */
    private Map<String, String> builderStandardRequestParam(List<ApiRequestParam> apiRequstParams) {
        Map<String, String> requestParams = new HashMap<String, String>();
        if (apiRequstParams == null || apiRequstParams.size() <= 0) {
            return requestParams;
        }

        for (ApiRequestParam apiRequestParam : apiRequstParams) {
            if (StringUtils.equals(apiRequestParam.getType(), Constant.INTEGER)) {
                requestParams.put(apiRequestParam.getName(), DataUtils.generateNumber(1));
                continue;
            }

            if (StringUtils.equals(apiRequestParam.getType(), Constant.STRING)) {
                requestParams.put(apiRequestParam.getName(), DataUtils.generateString(6));
                continue;
            }

            if (StringUtils.equals(apiRequestParam.getType(), Constant.BOOLEAN)) {
                requestParams.put(apiRequestParam.getName(), DataUtils.generateBoolean());
                continue;
            }

            if (StringUtils.equals(apiRequestParam.getType(), Constant.DATE)) {
                requestParams.put(apiRequestParam.getName(), DataUtils.generateDate());
                continue;
            }

            if (StringUtils.equals(apiRequestParam.getType(), Constant.DOUBLE)) {
                requestParams.put(apiRequestParam.getName(), DataUtils.generateDouble());
                continue;
            }
        }

        return requestParams;
    }

    /**
     * 生成参数配置长度
     * 
     * @param apiRequstParam
     * @return
     */
    private static Map<String, String> builderMaxRequestParam(List<ApiRequestParam> apiRequstParams, int maxLength) {
        Map<String, String> requestParams = new HashMap<String, String>();
        if (apiRequstParams == null || apiRequstParams.size() <= 0) {
            return requestParams;
        }

        for (ApiRequestParam apiRequestParam : apiRequstParams) {
            if (StringUtils.equals(apiRequestParam.getType(), Constant.INTEGER)) {
                requestParams.put(apiRequestParam.getName(), DataUtils.generateNumber(maxLength));
                continue;
            }

            if (StringUtils.equals(apiRequestParam.getType(), Constant.STRING)) {
                requestParams.put(apiRequestParam.getName(), DataUtils.generateString(maxLength));
                continue;
            }

            if (StringUtils.equals(apiRequestParam.getType(), Constant.BOOLEAN)) {
                requestParams.put(apiRequestParam.getName(), DataUtils.generateBoolean());
                continue;
            }

            if (StringUtils.equals(apiRequestParam.getType(), Constant.DATE)) {
                requestParams.put(apiRequestParam.getName(), DataUtils.generateDate());
                continue;
            }

            if (StringUtils.equals(apiRequestParam.getType(), Constant.DOUBLE)) {
                requestParams.put(apiRequestParam.getName(), DataUtils.generateDouble());
                continue;
            }
        }

        return requestParams;
    }

    @SuppressWarnings("static-access")
    @Override
    public void run() {
        String useAnalysisStr = JDocConfig.getValue("use.analysis");
        boolean useAnalysis = false;
        if (StringUtils.isNotBlank(useAnalysisStr)) {
            useAnalysis = Boolean.parseBoolean(useAnalysisStr);
        }
        if (!useAnalysis) {
            return;
        }

        String prefixUrl = JDocConfig.getValue("analysis.prefix.url");

        if (StringUtils.isBlank(prefixUrl)) {
            prefixUrl = DEFAULT_PREFIX_URL;
        }

        int tryTimes = 3;
        boolean isContinue = true;
        while (isContinue) {
            URL url;
            try {
                url = new URL(prefixUrl);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                int state = con.getResponseCode();
                if (state == 404 || state == 200 || state == 500) {
                    analysisApi();
                    break;
                }
                tryTimes--;
                if (tryTimes <= 0) {
                    isContinue = false;
                    throw new JDocException("the server start time out of 15 seconds,stop analysis api action");
                }
            } catch (Exception e) {
                try {
                    this.sleep(5000);
                } catch (InterruptedException e1) {

                }
                tryTimes--;
                if (tryTimes <= 0) {
                    isContinue = false;
                    throw new JDocException("the server start time out of 15 seconds,stop analysis api action");
                }
            }

        }

    }

}
