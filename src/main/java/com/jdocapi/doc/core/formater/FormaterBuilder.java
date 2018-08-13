package com.jdocapi.doc.core.formater;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.jdocapi.doc.bean.Api;
import com.jdocapi.doc.core.config.JDocConfig;
import com.jdocapi.doc.core.constant.Constant;
import com.jdocapi.doc.core.formater.impl.HtmlFormater;

public class FormaterBuilder implements Formater{
    
    private Formater formater;

    @Override
    public void output(List<Api> apis) {
        formater.output(apis);
    }
    
    public Formater builder(){
        String apiParserName = JDocConfig.getValue("parser.formater");
        
        if(StringUtils.equals(apiParserName, Constant.HTML_FORMATER)){
            formater = new HtmlFormater(); 
        }
        
        if(formater==null){
            formater = new HtmlFormater();
        }
        
        return formater;
        
    } 
    
    

}
