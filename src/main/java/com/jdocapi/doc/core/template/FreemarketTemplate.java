package com.jdocapi.doc.core.template;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreemarketTemplate {

    static Configuration cfg = new Configuration();

    static {
        cfg.setDefaultEncoding("UTF-8");
        cfg.setClassForTemplateLoading(FreemarketTemplate.class, "/com/jdocapi/doc/core/template/ftl");
    }

    public static void output(String templatePath,String outputPath,Map<Object,Object> paras){
        try {
            cfg.clearTemplateCache();
            Template template = cfg.getTemplate(templatePath);
            File file = new File(outputPath);
            if(!file.exists()){
                if(!file.getParentFile().exists()){
                    file.getParentFile().mkdir();
                }
                file.createNewFile();
            }
            Writer out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file), "UTF-8"));
            template.process(paras, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

}
