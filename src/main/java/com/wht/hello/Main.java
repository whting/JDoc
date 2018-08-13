package com.wht.hello;

import com.jdocapi.doc.core.api.service.ServiceApiDocConfig;
import com.jdocapi.doc.core.api.springmvc.SpringMVCApiDocConfig;

public class Main {
	public static void main(String[] args) {
//		SpringMVCApiDocConfig springMVCApiDocConfig = new SpringMVCApiDocConfig();
//		springMVCApiDocConfig.setConfigFilePath("jdoc.properties");
//		springMVCApiDocConfig.start();

		ServiceApiDocConfig serviceApiDocConfig = new ServiceApiDocConfig();
		serviceApiDocConfig.setConfigFilePath("jdoc.properties");
		serviceApiDocConfig.start();
	}
}
