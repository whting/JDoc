package com.jdocapi.doc.core.api;

import java.util.ArrayList;
import java.util.List;

import com.jdocapi.doc.utils.DataUtils;
import org.apache.commons.lang3.StringUtils;

import com.jdocapi.doc.bean.Api;
import com.jdocapi.doc.bean.ApiRequestParam;
import com.jdocapi.doc.bean.ApiResponseParam;
import com.jdocapi.doc.core.constant.Constant;
import com.jdocapi.doc.core.exception.JDocException;
import com.jdocapi.doc.core.parser.BeanDocumentParser;
import com.jdocapi.doc.core.parser.BeanParamContainer;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.SeeTag;
import com.sun.javadoc.Tag;

public abstract class AbstractApiParser {

    /**
     * 过滤出Controller文档
     * 
     * @param fileNames
     * @return
     */
    public abstract List<String> filterController(List<String> fileNames);

    public abstract List<Api> generateApi(ClassDoc[] classDocs);

    public List<ApiRequestParam> generateApiReqeustParam(Tag[] tags) {
        List<ApiRequestParam> apiRequestParams = new ArrayList<ApiRequestParam>();
        List<Tag> paramTags = getTagsByTag(tags, "@param");
        List<Tag> paramBeanTags = getTagsByTag(tags, "@paramBean");
        for (Tag tag : paramTags) {
            ApiRequestParam apiRequestParam = new ApiRequestParam();
            String tagText = tag.text();
            String[] descs = tagText.split("\\s+");
            if (descs.length < 2) {
             //   throw new JDocException("the param formater dose not match jdoc formater " + tagText);
                continue;
            }
            if(descs.length < 4 ){
                if(descs.length == 3){
                    apiRequestParam.setType(descs[2]);
                    apiRequestParam.setRequired("必填");
                }
                if(descs.length == 2){
                    apiRequestParam.setType("String");
                    apiRequestParam.setRequired("必填");
                }
            }else{
                apiRequestParam.setType(descs[2]);
                apiRequestParam.setRequired(descs[3]);
            }
            apiRequestParam.setName(descs[0]);
            apiRequestParam.setDesc(descs[1]);

            apiRequestParams.add(apiRequestParam);
        }

        if (paramBeanTags != null && !paramBeanTags.isEmpty()) {
            generateSeeTag(tags);
            for (Tag paramBeanTag : paramBeanTags) {
                String beanName = paramBeanTag.text();
                List<ApiRequestParam> beanApiRequestParams = BeanParamContainer.getApiRequestParamParam(beanName);

                for(ApiRequestParam requestParam : beanApiRequestParams){
                    String type = requestParam.getType();
                    if(!DataUtils.isSimpleType(type)){
                        requestParam.setSubApiRequestParam(BeanParamContainer.getApiRequestParamParam(type));
                    }
                }

                if(beanApiRequestParams==null){
                    throw new JDocException("not find "+beanName+" bean on see tag");
                }
                apiRequestParams.addAll(beanApiRequestParams);
            }
        }

        return apiRequestParams;
    }

    public List<ApiResponseParam> generateApiResponseParam(Tag[] tags) {
        List<ApiResponseParam> apiResponseParams = new ArrayList<ApiResponseParam>();
        List<Tag> paramTags = getTagsByTag(tags, "@respParam");
        List<Tag> respParamBeanTags = getTagsByTag(tags, "@respParamBean");
        List<Tag> respSubParamBeanTags = getTagsByTag(tags, "@respSubParamBean");
        for (Tag tag : paramTags) {
            ApiResponseParam apiResponseParam = new ApiResponseParam();
            String tagText = tag.text();
            String[] descs = tagText.split("\\s+");
            if (descs.length < 2) {
                throw new JDocException("the param formater dose not match jdoc formater" + tagText);
            }
            if(descs.length < 4 ){
                if(descs.length == 3){
                    apiResponseParam.setType(descs[2]);
                    apiResponseParam.setRequired("必填");
                }
                if(descs.length == 2){
                    apiResponseParam.setType("String");
                    apiResponseParam.setRequired("必填");
                }
            }else{
                apiResponseParam.setType(descs[2]);
                apiResponseParam.setRequired(descs[3]);
            }
            apiResponseParam.setName(descs[0]);
            apiResponseParam.setDesc(descs[1]);

            apiResponseParams.add(apiResponseParam);
        }

        if (respParamBeanTags != null && !respParamBeanTags.isEmpty()) {
            generateSeeTag(tags);
            for (Tag paramBeanTag : respParamBeanTags) {
                String beanName = paramBeanTag.text();
                List<ApiResponseParam> beanapiResponseParams = BeanParamContainer.getApiResponseParam(beanName);

                for(ApiResponseParam responseParam : beanapiResponseParams){
                    String type = responseParam.getType();
                    if(!DataUtils.isSimpleType(type)){
                        if(("Object".equals(type) || "Serializable".equals(type)) && respSubParamBeanTags !=null && !respSubParamBeanTags.isEmpty()){
                            responseParam.setType(respSubParamBeanTags.get(0).text());
                            responseParam.setSubApiResponseParam(BeanParamContainer.getApiResponseParam(respSubParamBeanTags.get(0).text()));
                        }else {
                            responseParam.setSubApiResponseParam(BeanParamContainer.getApiResponseParam(responseParam.getType()));
                        }
                    }
                }

                if(beanapiResponseParams==null){
                    throw new JDocException("not find "+beanName+" bean on see tag");
                }
                apiResponseParams.addAll(beanapiResponseParams);
            }
        }
        return apiResponseParams;
    }


    public String getTitle(Tag[] tags) {
        List<Tag> targetTags = getTagsByTag(tags, "@title");
        if (targetTags != null && !targetTags.isEmpty()) {
            return targetTags.get(0).text();
        }

        return "";
    }
    
    public String getAuthor(Tag[] tags) {
        List<Tag> targetTags = getTagsByTag(tags, "@author");
        if (targetTags != null && !targetTags.isEmpty()) {
            return targetTags.get(0).text();
        }

        return "";
    }

    public String getRespBody(Tag[] tags) {
        List<Tag> targetTags = getTagsByTag(tags, "@respBody");
        if (targetTags != null && !targetTags.isEmpty()) {
            return targetTags.get(0).text();
        }

        return "";
    }

    public String getReturnDesc(Tag[] tags) {
        List<Tag> targetTags = getTagsByTag(tags, "@return");
        if (targetTags != null && !targetTags.isEmpty()) {
            return targetTags.get(0).text();
        }

        return "";
    }

    public void generateSeeTag(Tag[] tags) {
        List<Tag> targetTags = getTagsByTag(tags, "@see");
        List<String> beanJavaFileNames = new ArrayList<String>();
        for (Tag tag : targetTags) {
            SeeTag seeTag = (SeeTag) tag;
            String className = seeTag.referencedClassName();
            beanJavaFileNames.add(Constant.JAVA_FILE_PATH + className.replaceAll("\\.", "/") + Constant.JAVA_FILE_SUFFIX);
        }
        if (!beanJavaFileNames.isEmpty()) {
            beanJavaFileNames.add(0, "-doclet");
            beanJavaFileNames.add(1, BeanDocumentParser.class.getName());
            String[] docArgs = beanJavaFileNames.toArray(new String[beanJavaFileNames.size()]);
            com.sun.tools.javadoc.Main.execute(docArgs);
        }
    }

    public List<Tag> getTagsByTag(Tag[] tags, String tagName) {
        List<Tag> targetTags = new ArrayList<Tag>();
        for (Tag tag : tags) {
            if (StringUtils.equals(tag.name(), tagName)) {
                targetTags.add(tag);
            }
        }
        return targetTags;
    }
}
