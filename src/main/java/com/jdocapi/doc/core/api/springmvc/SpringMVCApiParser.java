package com.jdocapi.doc.core.api.springmvc;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jdocapi.doc.bean.Api;
import com.jdocapi.doc.bean.ApiAction;
import com.jdocapi.doc.bean.ApiRequestParam;
import com.jdocapi.doc.bean.ApiResponseParam;
import com.jdocapi.doc.core.api.AbstractApiParser;
import com.jdocapi.doc.core.config.JDocConfig;
import com.jdocapi.doc.core.constant.Constant;
import com.jdocapi.doc.core.exception.JDocException;
import com.jdocapi.doc.utils.ClassUtils;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.Tag;

public class SpringMVCApiParser extends AbstractApiParser {

	@Override
	public List<String> filterController(List<String> fileNames) {

		List<String> controllerFileNames = new ArrayList<String>();

		for (String fileName : fileNames) {
			String packageFileName = fileName
					.substring(Constant.JAVA_FILE_PATH.length(), fileName.length() - Constant.JAVA_FILE_SUFFIX.length())
					.replace(File.separator, ".");
			try {
				Class<?> fileClass = Class.forName(packageFileName);
				Controller controller = fileClass.getAnnotation(Controller.class);
				RestController restController = fileClass.getAnnotation(RestController.class);
				if (controller != null || restController != null) {
					controllerFileNames.add(fileName);
				}
			} catch (ClassNotFoundException e) {

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
			api.setTitle(commentText);
			Tag[] classDocTags = classDoc.tags();
			String classAuthor = getTitle(classDocTags);
			MethodDoc[] methodDocs = classDoc.methods(false);

			Class<?> controllerClass = null;
			try {
				controllerClass = Class.forName(classDoc.qualifiedTypeName());
			} catch (ClassNotFoundException e) {

			}

			RequestMapping controllerRequestMapping = controllerClass.getAnnotation(RequestMapping.class);

			String url = "";
			if (controllerRequestMapping != null) {
				url = controllerRequestMapping.value()[0];
			}

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
				if(method==null){
					continue;
				}
				RequestMapping methodReqeustMapping = method.getAnnotation(RequestMapping.class);
				
				if(methodReqeustMapping==null)continue;
				
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
				apiAction.setMethodSign(generateMethodSign(method,controllerClass.getName()));
				apiAction.setRequestType(requestType);
				apiAction.setTitle(title);
				apiAction.setRespText(respBody);
				apiAction.setRespParams(apiResponseParams);
				apiAction.setReqParams(apiRequestParams);
				apiAction.setUrl(requestUrl);
				apiAction.setDesc(methodDoc.commentText());
				apiActions.add(apiAction);
			}

			api.setApiActions(apiActions);
			apis.add(api);
		}
		return apis;

	}

	private String generateRequestUrl(Method method, String url) {
		RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
		if (requestMapping == null) {
			throw new JDocException("please add requestMapping annotion on this method " + method.getName());
		}

		return JDocConfig.getValue("api.prefix") + url + requestMapping.value()[0];
	}

	private String getRequestType(Method method) {
		StringBuilder requestType = new StringBuilder();
		RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
		RequestMethod[] requestMethods = requestMapping.method();
		if (requestMethods != null) {
			for (int i = 0; i < requestMethods.length; i++) {
				if (RequestMethod.GET == requestMethods[i]) {
					requestType.append(Constant.GET).append(",");
				}

				if (RequestMethod.POST == requestMethods[i]) {
					requestType.append(Constant.POST).append(",");
				}
			}
		}
		if (requestType.length() > 0) {
			return requestType.substring(0, requestType.length() - 1);
		}

		return Constant.POST + "," + Constant.GET;
	}

	private String generateMethodSign(Method method, String className) {

		return className + "." + method.getName();
	}
}
