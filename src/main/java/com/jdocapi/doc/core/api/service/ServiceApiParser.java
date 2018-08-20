package com.jdocapi.doc.core.api.service;

import com.jdocapi.doc.bean.Api;
import com.jdocapi.doc.bean.ApiAction;
import com.jdocapi.doc.bean.ApiRequestParam;
import com.jdocapi.doc.bean.ApiResponseParam;
import com.jdocapi.doc.core.api.AbstractApiParser;
import com.jdocapi.doc.core.constant.Constant;
import com.jdocapi.doc.utils.ClassUtils;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.Tag;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ServiceApiParser extends AbstractApiParser {

	@Override
	public List<String> filterController(List<String> fileNames) {

		List<String> serviceFileNames = new ArrayList<String>();

		for (String fileName : fileNames) {
			String packageFileName = fileName.substring(Constant.JAVA_FILE_PATH.length(), fileName.length() - Constant.JAVA_FILE_SUFFIX.length()).replace(File.separator, ".");
			try {
				Class<?> fileClass = Class.forName(packageFileName);
				if(fileClass.isInterface()){
					serviceFileNames.add(fileName);
				}
		} catch(ClassNotFoundException e){

		}
	}
		return serviceFileNames;
}

	@Override
	public List<Api> generateApi(ClassDoc[] classDocs) {

		List<Api> apis = new ArrayList<Api>();
		for (ClassDoc classDoc : classDocs) {

			Api api = new Api();

			Tag[] classDocTags = classDoc.tags();
			String classAuthor = getAuthor(classDocTags);
			if(classAuthor == null || "".equals(classAuthor)){
				continue;
			}

			api.setTitle(classDoc.commentText());
			MethodDoc[] methodDocs = classDoc.methods(false);

			Class<?> controllerClass = null;
			try {
				controllerClass = Class.forName(classDoc.qualifiedTypeName());
			} catch (ClassNotFoundException e) {

			}

			String className = controllerClass.getName();

			String url = "";
			if (className != null) {
				url = className;
			}

			List<ApiAction> apiActions = new ArrayList<ApiAction>(methodDocs.length);

			for (MethodDoc methodDoc : methodDocs) {

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
				String respBody = getRespBody(tags);
				String requestUrl = generateRequestUrl(method, url);
				String requestType = getRequestType(method);

				String returnDesc = getReturnDesc(tags);
				apiAction.setMethodSign(generateRequestUrl(method,className));
				apiAction.setRequestType(requestType);
				apiAction.setTitle(title);
				apiAction.setRespText(respBody);
				apiAction.setRespParams(apiResponseParams);
				apiAction.setReqParams(apiRequestParams);
				apiAction.setUrl(requestUrl);
				apiAction.setDesc(methodDoc.commentText());
				apiAction.setReturnDesc(returnDesc);
				apiActions.add(apiAction);
			}

			api.setApiActions(apiActions);
			apis.add(api);
		}
		return apis;

	}

	private String generateRequestUrl(Method method, String className) {

		return className + "." + method.getName();
	}

	private String getRequestType(Method method) {

		return "dubbo";
	}

}
