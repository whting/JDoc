package com.jdocapi.doc.core.formater.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jdocapi.doc.bean.Api;
import com.jdocapi.doc.core.config.JDocConfig;
import com.jdocapi.doc.core.constant.Constant;
import com.jdocapi.doc.core.formater.Formater;
import com.jdocapi.doc.core.template.FreemarketTemplate;

public class WordFormater implements Formater {

	@Override
	public void output(List<Api> apis) {
		Map<Object, Object> paras = new HashMap<Object, Object>();
		paras.put("apis", apis);
		FreemarketTemplate.output(Constant.WORD_TEMPALTE, JDocConfig.getValue("out.path"), paras);
	}

}
