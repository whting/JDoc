package com.jdocapi.doc.core.parser;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.jdocapi.doc.core.constant.Constant;
import com.sun.javadoc.*;
import org.apache.commons.lang3.StringUtils;

import com.jdocapi.doc.bean.Param;
import com.jdocapi.doc.utils.DataUtils;

/**
 * bean文档解析器
 * 
 * @author haoting.wang
 */
public class BeanDocumentParser {

    public static boolean start(RootDoc root) {

        ClassDoc[] beanClassDocs = root.classes();

        for (ClassDoc beanClassDoc : beanClassDocs) {

            List<Param> params = BeanParamContainer.getParam(beanClassDoc.name());
            if (params != null) {
                continue;
            }

            params = new ArrayList<Param>();

            FieldDoc[] fieldDocs = beanClassDoc.fields(false);
            Field[] fields = null;
            try {
                Class<?> classBean = Class.forName(beanClassDoc.qualifiedName());
                fields = classBean.getDeclaredFields();
            } catch (ClassNotFoundException e) {
                System.out.println(e);
            }

            for (int i = 0; i < fieldDocs.length; i++) {
                FieldDoc fieldDoc = fieldDocs[i];
                String required="";
                if(fields!=null){
                    Field field = fields[i];
                    required = getJSR303ValidationMessage(field);
                }
                String typeName = fieldDoc.type().typeName();
                String commentText = fieldDoc.commentText();

                Param param = new Param();
                param.setName(fieldDoc.name());

                param.setType(typeName);
                if (!DataUtils.isSimpleType(typeName)) {
                    Tag[] tags = fieldDoc.tags();
                    generateSeeTag(tags);
                    List<Param> paramList;
                    List<Tag> subType = getTagsByTag(tags, "@subType");
                    if(subType !=null && subType.size()>0){
                        paramList = BeanParamContainer.getParam(subType.get(0).text());
                    }else{
                        paramList = BeanParamContainer.getParam(typeName);
                    }
                    if (paramList != null && !paramList.isEmpty()) {
                        param.setParams(paramList);
                    }
                }

                String[] comments = commentText.split("\\|");
                if (comments.length > 1) {
                    param.setDesc(comments[0]);
                    param.setRequired(comments[1]);
                }

                if (comments.length == 1) {
                    param.setDesc(comments[0]);
                }

                if (StringUtils.isNotBlank(required)) {
                    param.setRequired(required);
                }

                params.add(param);
            }
            BeanParamContainer.putBeanParam(params, beanClassDoc.name());
        }

        return true;
    }

    public static void generateSeeTag(Tag[] tags) {
        List<Tag> targetTags = getTagsByTag(tags, "@see");
        List<String> beanJavaFileNames = new ArrayList<String>();
        for (Tag tag : targetTags) {
            SeeTag seeTag = (SeeTag) tag;
            String className = seeTag.referencedClassName();
            beanJavaFileNames.add(Constant.JAVA_FILE_PATH + className.replaceAll("\\.", "/")
                                  + Constant.JAVA_FILE_SUFFIX);
        }
        if (!beanJavaFileNames.isEmpty()) {
            beanJavaFileNames.add(0, "-doclet");
            beanJavaFileNames.add(1, BeanDocumentParser.class.getName());
            String[] docArgs = beanJavaFileNames.toArray(new String[beanJavaFileNames.size()]);
            com.sun.tools.javadoc.Main.execute(docArgs);
        }
    }

    public static List<Tag> getTagsByTag(Tag[] tags, String tagName) {
        List<Tag> targetTags = new ArrayList<Tag>();
        for (Tag tag : tags) {
            if (StringUtils.equals(tag.name(), tagName)) {
                targetTags.add(tag);
            }
        }
        return targetTags;
    }

    /**
     * 获取JSR303模版提示信息
     * 
     * @param field
     * @return
     */
    private static String getJSR303ValidationMessage(Field field) {
        StringBuilder message = new StringBuilder();
        Annotation[] annotions = field.getAnnotations();
        if (annotions == null || annotions.length == 0) {
            return null;
        }

        for (Annotation annotation : annotions) {
            if (annotation instanceof NotNull) {
                NotNull notNull = (NotNull) annotation;
                message.append(notNull.message()).append(",");
                continue;
            }
            if (annotation instanceof Null) {
                Null nullAnnotation = (Null) annotation;
                message.append(nullAnnotation.message()).append(",");
                continue;
            }
            if (annotation instanceof DecimalMax) {
                DecimalMax decimalMax = (DecimalMax) annotation;
                message.append(decimalMax.message()).append(",");
                continue;
            }
            if (annotation instanceof DecimalMin) {
                DecimalMin decimalMin = (DecimalMin) annotation;
                message.append(decimalMin.message()).append(",");
                continue;
            }
            if (annotation instanceof Digits) {
                Digits digits = (Digits) annotation;
                message.append(digits.message()).append(",");
                continue;
            }
            if (annotation instanceof Future) {
                Future future = (Future) annotation;
                message.append(future.message()).append(",");
                continue;
            }
            if (annotation instanceof Max) {
                Max max = (Max) annotation;
                message.append(max.message()).append(",");
                continue;
            }
            if (annotation instanceof Min) {
                Min min = (Min) annotation;
                message.append(min.message()).append(",");
                continue;
            }
            if (annotation instanceof Past) {
                Past past = (Past) annotation;
                message.append(past.message()).append(",");
                continue;
            }
            if (annotation instanceof Pattern) {
                Pattern pattern = (Pattern) annotation;
                message.append(pattern.message()).append(",");
                continue;
            }
            if (annotation instanceof Size) {
                Size size = (Size) annotation;
                message.append(size.message()).append(",");
                continue;
            }
        }
        if (StringUtils.isNotBlank(message)) {
            return message.toString().substring(0, message.length() - 1);
        }
        return null;
    }
}
