package com.jdocapi.doc.utils;

import com.jdocapi.doc.core.constant.Constant;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class DataUtils {

    private static final char[]   STRING_SEED_POOL  = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N',
                                                        'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a',
                                                        'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p',
                                                        'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2',
                                                        '3', '4', '5', '6', '7', '8', '9' };

    private static final char[]   NUM_SEED_POOL     = { '1', '2', '3', '4', '5', '6', '7', '8', '9' };

    private static final String[] BOOLEAN_SEED_POOL = { "true", "false" };

    public static String generateString(int length) {
        return RandomStringUtils.random(length, STRING_SEED_POOL);
    }

    public static String generateNumber(int length) {
        return RandomStringUtils.random(length, NUM_SEED_POOL);
    }

    public static String generateDouble() {
        Double data = RandomUtils.nextDouble(1, 1000);
        DecimalFormat df = new DecimalFormat("######0.00");
        return df.format(data);
    }

    public static String generateBoolean() {
        Random random = new Random();
        int result = random.nextInt(1);
        return BOOLEAN_SEED_POOL[result];
    }

    public static String generateDate() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    public static String getType(String typeName) {

        if (isByte(typeName)) {
            return Constant.BYTE;
        }

        if (isShort(typeName)) {
            return Constant.SHORT;
        }

        if (isInteger(typeName)) {
            return Constant.INTEGER;
        }

        if (isLong(typeName)) {
            return Constant.LONG;
        }
        if (isFloat(typeName)) {
            return Constant.FLOAT;
        }
        if (isDouble(typeName)) {
            return Constant.DOUBLE;
        }
        if (isBoolean(typeName)) {
            return Constant.BOOLEAN;
        }
        if (isChar(typeName)) {
            return Constant.CHAR;
        }
        if (isString(typeName)) {
            return Constant.STRING;
        }
        if (isDate(typeName)) {
            return Constant.DATE;
        }
        return typeName;
    }


    public static boolean isSimpleType(String typeName) {

        if (isByte(typeName) || isShort(typeName) || isInteger(typeName) || isLong(typeName) || isBoolean(typeName)
            || isChar(typeName) || isString(typeName) || isDate(typeName) || isFloat(typeName) || isDouble(typeName)) {

            return true;
        }

        return false;
    }

    public static boolean isByte(String typeName) {
        if ("byte".equals(typeName) || "Byte".equals(typeName)) {
            return true;
        }
        return false;
    }

    public static boolean isShort(String typeName) {
        if ("short".equals(typeName) || "Short".equals(typeName)) {
            return true;
        }
        return false;
    }

    public static boolean isInteger(String typeName) {
        if ("Integer".equals(typeName) || "int".equals(typeName)) {
            return true;
        }
        return false;
    }

    public static boolean isLong(String typeName) {
        if ("long".equals(typeName) || "Long".equals(typeName)) {
            return true;
        }
        return false;
    }

    public static boolean isFloat(String typeName) {
        if ("float".equals(typeName) || "Float".equals(typeName)) {
            return true;
        }

        return false;
    }

    public static boolean isDouble(String typeName) {
        if ("Double".equals(typeName) || "double".equals(typeName)) {
            return true;
        }

        return false;
    }

    public static boolean isBoolean(String typeName) {
        if ("Boolean".equals(typeName) || "boolean".equals(typeName)) {
            return true;
        }
        return false;
    }

    public static boolean isChar(String typeName) {
        if ("Char".equals(typeName) || "char".equals(typeName) || "String".equals(typeName)) {
            return true;
        }
        return false;
    }

    public static boolean isString(String typeName) {
        if ("String".equals(typeName)) {
            return true;
        }
        return false;
    }

    public static boolean isDate(String typeName) {
        if ("Date".equals(typeName) || "Timestamp".equals(typeName)) {
            return true;
        }
        return false;
    }
}
