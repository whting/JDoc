package com.jdocapi.doc.core.api.jfinal;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.jdocapi.doc.bean.Api;
import com.jdocapi.doc.bean.ApiAction;
import com.jdocapi.doc.bean.ApiRequestParam;
import com.jdocapi.doc.bean.ApiResponseParam;
import com.jdocapi.doc.core.api.AbstractApiParser;
import com.jdocapi.doc.core.config.JDocConfig;
import com.jdocapi.doc.core.constant.Constant;
import com.jdocapi.doc.utils.ClassUtils;
import com.jfinal.aop.Before;
import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.GET;
import com.jfinal.ext.interceptor.POST;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.Tag;

/**
 * JFinal解析器
 * 
 * @author haoting.wang
 */
public class JFinalApiParser extends AbstractApiParser {

    @Override
    public List<String> filterController(List<String> fileNames) {
        List<String> controllerFileNames = new ArrayList<String>();
        for (String fileName : fileNames) {
            String packageFileName = fileName.substring(Constant.JAVA_FILE_PATH.length(),
                                                        fileName.length() - Constant.JAVA_FILE_SUFFIX.length()).replace(File.separator,
                                                                                                                        ".");
            try {
                Class<?> fileClass = Class.forName(packageFileName);
                if (fileClass.newInstance() instanceof Controller) {
                    controllerFileNames.add(fileName);
                }
            } catch (Exception e) {

            }
        }
        return controllerFileNames;
    }

    @Override
    public List<Api> generateApi(ClassDoc[] classDocs) {
        List<Api> apis = new ArrayList<Api>();
        for (ClassDoc classDoc : classDocs) {

            String commentText = classDoc.commentText();
            if (StringUtils.isBlank(commentText)) {
                continue;
            }
            Api api = new Api();

            Tag[] classDocTags = classDoc.tags();
            String classAuthor = getTitle(classDocTags);

            api.setTitle(commentText);

            MethodDoc[] methodDocs = classDoc.methods(false);

            Class<?> controllerClass = null;
            try {
                controllerClass = Class.forName(classDoc.qualifiedTypeName());
            } catch (ClassNotFoundException e) {

            }

            String url = JFinalApiDocConfig.getControllerKey(classDoc.name());

            List<ApiAction> apiActions = new ArrayList<ApiAction>(methodDocs.length);

            for (MethodDoc methodDoc : methodDocs) {
                String methodDocCommentText = methodDoc.commentText();
                if (StringUtils.isBlank(methodDocCommentText)) {
                    continue;
                }

                ApiAction apiAction = new ApiAction();

                Class<?>[] paramTypes = null;
                Method method = null;
                try {
                    paramTypes = ClassUtils.getParamTypes(methodDoc);
                    method = controllerClass.getDeclaredMethod(methodDoc.name(), paramTypes);
                } catch (Exception e) {

                }
                Tag[] tags = methodDoc.tags();

                List<ApiRequestParam> apiRequestParams = generateApiReqeustParam(tags);
                List<ApiResponseParam> apiResponseParams = generateApiResponseParam(tags);
                String title = getTitle(tags);
                String methodAuthor = getAuthor(tags);
                String respBody = getRespBody(tags);
                String requestUrl = generateRequestUrl(method, url);
                String requestType = getRequestType(method);

                if (StringUtils.isBlank(methodAuthor)) {
                    apiAction.setAuthor(classAuthor);
                } else {
                    apiAction.setAuthor(methodAuthor);
                }
                apiAction.setRequestType(requestType);
                apiAction.setTitle(title);
                apiAction.setRespText(respBody);
                apiAction.setRespParams(apiResponseParams);
                apiAction.setReqParams(apiRequestParams);
                apiAction.setUrl(requestUrl);
                apiAction.setDesc(methodDocCommentText);
                apiActions.add(apiAction);
            }

            api.setApiActions(apiActions);
            apis.add(api);
        }
        return apis;
    }

    private String generateRequestUrl(Method method, String parentUrl) {

        ActionKey actionKey = method.getAnnotation(ActionKey.class);

        if (actionKey == null) {
            return JDocConfig.getValue("api.prefix") + parentUrl + "/" + method.getName();
        }

        return JDocConfig.getValue("api.prefix") + actionKey.value();

    }

    private String getRequestType(Method method) {
        StringBuilder requestType = new StringBuilder();
        Before before = method.getAnnotation(Before.class);
        if (before != null) {
            Class<? extends Interceptor>[] classes = before.value();
            for (Class<? extends Interceptor> interceptor : classes) {
                if (interceptor == POST.class) {
                    requestType.append(Constant.POST).append(",");
                }
                if (interceptor == GET.class) {
                    requestType.append(Constant.GET).append(",");
                }
            }
        }

        if (requestType.length() > 0) {
            return requestType.substring(0, requestType.length() - 1);
        }

        return Constant.POST + "," + Constant.GET;
    }

}
