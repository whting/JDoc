package com.jdocapi.doc.core.formater.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jdocapi.doc.bean.Api;
import com.jdocapi.doc.core.config.JDocConfig;
import com.jdocapi.doc.core.constant.Constant;
import com.jdocapi.doc.core.formater.Formater;
import com.jdocapi.doc.core.template.FreemarketTemplate;

public class HtmlFormater implements Formater{

    @SuppressWarnings("unchecked")
	@Override
    public void output(List<Api> apis) {
         String json = JSON.toJSONString(apis, new SerializerFeature[]{SerializerFeature.DisableCircularReferenceDetect});
         Map<Object,Object> paras = new HashMap<Object,Object>();
         paras.put("api", json);
         String cookie = JDocConfig.getValue("cookie");
         if(StringUtils.isNotBlank(cookie)){
        	 Map<String,Object> cookies = JSON.parseObject(cookie, Map.class);
        	  paras.put("cookies", cookies);
         }
         
         String requestType = JDocConfig.getValue("request.type");
         
         if(StringUtils.isBlank(requestType)){
        	 requestType = Constant.REQUEST_TYPE_JSON;
         }
         
         paras.put("requestType", requestType);
         
         FreemarketTemplate.output(Constant.HTML_TEMPALTE,JDocConfig.getValue("out.path"), paras);
    }
}
