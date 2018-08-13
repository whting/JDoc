package com.jdocapi.doc.core;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.jdocapi.doc.core.api.ApiBuilder;
import com.jdocapi.doc.core.config.JDocConfig;
import com.jdocapi.doc.core.exception.JDocException;
import com.jdocapi.doc.core.parser.BeanDocumentParser;
import com.jdocapi.doc.core.parser.DocumentParser;
import com.jdocapi.doc.utils.FileUtils;

public class JDocCore {
    
    private String configFileName;
    
    
    public void builder(){
        /**
         * 初始化加载配置文件
         */
        
        JDocConfig.instance(configFileName);
        /**
         * 获取扫描的包名
         */
        String packageName = JDocConfig.getValue("package.name");
        
        String beanPackageNameConfig = JDocConfig.getValue("bean.package.name");
        
        if(StringUtils.isBlank(packageName)){
            throw new JDocException("package.name para is not allow empty");
        }
        
        if(StringUtils.isNotBlank(beanPackageNameConfig)){
        	 String[] beanPackageNames = beanPackageNameConfig.split(",");
        	 List<String> beanFiles = new ArrayList<String>();
        	 for(String beanPackageName:beanPackageNames){
        		 beanFiles.addAll(FileUtils.getJavaFileNameByPackage(beanPackageName));
        	 }
        	 beanFiles.add(0, "-doclet");
        	 beanFiles.add(1, BeanDocumentParser.class.getName());
             String[] docArgs = beanFiles.toArray(new String[beanFiles.size()]);
             com.sun.tools.javadoc.Main.execute(docArgs);
        }
        
        List<String> files = FileUtils.getJavaFileNameByPackage(packageName);
        
        files = ApiBuilder.builder().filterController(files);
        
       
        files.add(0, "-doclet");
        files.add(1, DocumentParser.class.getName());

        String[] docArgs = files.toArray(new String[files.size()]);

        com.sun.tools.javadoc.Main.execute(docArgs);
        
    }
    
    
    public JDocCore setConfigFileName(String configFileName) {
        this.configFileName = configFileName;
        return this;
    }

    
    public String getConfigFileName() {
        return configFileName;
    }
    
    

}
