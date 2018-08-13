package com.jdocapi.doc.core.parser;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.jdocapi.doc.bean.Api;
import com.jdocapi.doc.core.analysis.ApiAnalysis;
import com.jdocapi.doc.core.api.AbstractApiParser;
import com.jdocapi.doc.core.api.ApiBuilder;
import com.jdocapi.doc.core.config.JDocConfig;
import com.jdocapi.doc.core.formater.Formater;
import com.jdocapi.doc.core.formater.FormaterBuilder;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.RootDoc;

/**
 * 文档解析器
 * 
 * @author haoting.wang
 *
 */
public class DocumentParser {

    public static boolean start(RootDoc root) {
        AbstractApiParser apiParser = ApiBuilder.builder();

        ClassDoc[] classDocs = root.classes();

        List<Api> apis = apiParser.generateApi(classDocs);

        String useAnalysisStr = JDocConfig.getValue("use.analysis");
        boolean useAnalysis = false;
        if (StringUtils.isNotBlank(useAnalysisStr)) {
            useAnalysis = Boolean.parseBoolean(useAnalysisStr);
        }
        if (useAnalysis) {
            new ApiAnalysis(apis).start();
        } else {
            Formater formater = new FormaterBuilder().builder();
            formater.output(apis);
        }
        return true;
    }

}
