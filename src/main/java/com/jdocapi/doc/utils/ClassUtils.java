package com.jdocapi.doc.utils;

import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.Parameter;

public class ClassUtils {
    /**
     * 获取指定方法的所有入参类型,便于反射
     *
     * @param methodDoc
     * @return
     * @throws ClassNotFoundException
     */
    public static Class<?>[] getParamTypes(MethodDoc methodDoc) throws ClassNotFoundException {
        Parameter[] parameters = methodDoc.parameters();
        Class<?>[] types = new Class[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            String className = parameters[i].type().qualifiedTypeName();
            types[i] = toBaseDataType(className);
            if (types[i] == null) {
                types[i] = Class.forName(className);
            }
        }
        return types;
    }

    /**
     * 将基本类型的转为class类型
     *
     * @param className
     * @return
     */
    private static Class<?> toBaseDataType(String className) {
        if ("byte".equals(className)) {
            return byte.class;
        } else if ("short".equals(className)) {
            return short.class;
        } else if ("int".equals(className)) {
            return int.class;
        } else if ("long".equals(className)) {
            return long.class;
        } else if ("float".equals(className)) {
            return float.class;
        } else if ("double".equals(className)) {
            return double.class;
        } else if ("boolean".equals(className)) {
            return boolean.class;
        } else if ("char".equals(className)) {
            return char.class;
        } else {
            return null;
        }
    }
}
