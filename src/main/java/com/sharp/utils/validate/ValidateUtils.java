package com.sharp.utils.validate;

import com.sharp.utils.validate.an.*;
import com.sharp.utils.validate.core.ValidateCache;
import com.sharp.utils.validate.core.ValidateHandler;
import com.sharp.utils.validate.utils.CommonUtil;
import com.sharp.utils.validate.utils.ReflectUtils;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Set;

/**
 * ValidateUtils
 */
public class ValidateUtils {

    private Object value;

    private ValidateUtils(Object value) {
        this.value = value;
    }

    /**
     * 新建校验实例，传入目标对象
     *
     * @param value 校验对象
     * @return ValidateUtils
     */
    public static ValidateUtils is(Object value) {
        return new ValidateUtils(value);
    }

    /**
     * 切换目标对象，不重新创建实例
     *
     * @param value 校验对象
     * @return ValidateUtils
     */
    public ValidateUtils and(Object value) {
        this.value = value;
        return this;
    }

    /**
     * 非空校验
     *
     * @return ValidateUtils
     */
    public ValidateUtils notNull() {
        return notNull(null);
    }

    /**
     * 非空校验
     *
     * @param msg 错误信息
     * @return ValidateUtils
     */
    public ValidateUtils notNull(String msg) {
        ValidateHandler.notNull(value, msg);
        return this;
    }

    /**
     * 正则校验
     *
     * @param regex 正则表达式
     * @return ValidateUtils
     */
    public ValidateUtils regex(String regex) {
        return regex(regex, null);
    }

    /**
     * 正则校验
     *
     * @param regex 正则表达式
     * @param msg   错误信息
     * @return ValidateUtils
     */
    public ValidateUtils regex(String regex, String msg) {
        ValidateHandler.regex(regex, value, msg);
        return this;
    }

    /**
     * 最大值校验
     *
     * @param max 最大值
     * @return ValidateUtils
     */
    public ValidateUtils max(Number max) {
        return max(max, null);
    }

    /**
     * 最大值校验
     *
     * @param max 最大值
     * @param msg 错误信息
     * @return ValidateUtils
     */
    public ValidateUtils max(Number max, String msg) {
        ValidateHandler.max(max, value, msg);
        return this;
    }

    /**
     * 最小值校验
     *
     * @param min 最小值
     * @return ValidateUtils
     */
    public ValidateUtils min(Number min) {
        return min(min, null);
    }

    /**
     * 最小值校验
     *
     * @param min 最小值
     * @param msg 错误信息
     * @return ValidateUtils
     */
    public ValidateUtils min(Number min, String msg) {
        ValidateHandler.min(min, value, msg);
        return this;
    }

    /**
     * 最大长度校验
     *
     * @param max 最大长度
     * @return ValidateUtils
     */
    public ValidateUtils maxLength(int max) {
        return maxLength(max, null);
    }

    /**
     * 最大长度校验
     *
     * @param max 最大长度
     * @param msg 错误信息
     * @return ValidateUtils
     */
    public ValidateUtils maxLength(int max, String msg) {
        ValidateHandler.maxLength(max, value, msg);
        return this;
    }

    /**
     * 最小长度校验
     *
     * @param min 最小长度
     * @return ValidateUtils
     */
    public ValidateUtils minLength(int min) {
        return minLength(min, null);
    }

    /**
     * 最小长度校验
     *
     * @param min 最小长度
     * @param msg 错误信息
     * @return ValidateUtils
     */
    public ValidateUtils minLength(int min, String msg) {
        ValidateHandler.minLength(min, value, msg);
        return this;
    }

    /**
     * 中文校验
     *
     * @return ValidateUtils
     */
    public ValidateUtils chinese() {
        return chinese(null);
    }

    /**
     * 中文校验
     *
     * @param msg 错误信息
     * @return ValidateUtils
     */
    public ValidateUtils chinese(String msg) {
        ValidateHandler.chinese(value, msg);
        return this;
    }

    /**
     * 英文校验
     *
     * @return ValidateUtils
     */
    public ValidateUtils english() {
        return english(null);
    }

    /**
     * 英文校验
     *
     * @param msg 错误信息
     * @return ValidateUtils
     */
    public ValidateUtils english(String msg) {
        ValidateHandler.english(value, msg);
        return this;
    }

    /**
     * 手机号校验
     *
     * @return ValidateUtils
     */
    public ValidateUtils phone() {
        return phone(null);
    }

    /**
     * 手机号校验
     *
     * @param msg 错误信息
     * @return ValidateUtils
     */
    public ValidateUtils phone(String msg) {
        ValidateHandler.phone(value, msg);
        return this;
    }

    /**
     * 邮箱校验
     *
     * @return ValidateUtils
     */
    public ValidateUtils email() {
        return email(null);
    }

    /**
     * 邮箱校验
     *
     * @param msg 错误信息
     * @return ValidateUtils
     */
    public ValidateUtils email(String msg) {
        ValidateHandler.email(value, msg);
        return this;
    }

    /**
     * 自定义日期格式校验
     *
     * @param format 格式
     * @return ValidateUtils
     */
    public ValidateUtils date(String format) {
        return date(format, null);
    }

    /**
     * 自定义日期格式校验
     *
     * @param format 格式
     * @param msg    错误信息
     * @return ValidateUtils
     */
    public ValidateUtils date(String format, String msg) {
        ValidateHandler.date(format, value, msg);
        return this;
    }

    /**
     * 身份证校验
     *
     * @return ValidateUtils
     */
    public ValidateUtils idCard() {
        return idCard(null);
    }

    /**
     * 身份证校验
     *
     * @param msg 错误信息
     * @return ValidateUtils
     */
    public ValidateUtils idCard(String msg) {
        ValidateHandler.idCard(value, msg);
        return this;
    }

    /**
     * IP地址校验
     *
     * @return ValidateUtils
     */
    public ValidateUtils ip() {
        return ip(null);
    }

    /**
     * IP地址校验
     *
     * @param msg 错误信息
     * @return ValidateUtils
     */
    public ValidateUtils ip(String msg) {
        ValidateHandler.ip(value, msg);
        return this;
    }

    /**
     * 对象校验（通过注解）
     *
     * @param value 校验对象
     * @return ValidateUtils
     */
    public static ValidateUtils check(Object value) {
        ValidateUtils validateUtils = new ValidateUtils(value);
        validateUtils.notNull();
        Class classType = value.getClass();
        Set<Field> fieldSet = ValidateCache.getInstance().getFieldsByClass(classType);
        if (null == fieldSet) {
            fieldSet = ReflectUtils.getFieldsByClass(value.getClass());
            ValidateCache.getInstance().setClassFields(classType, fieldSet);
        }
        if (CommonUtil.isNull(fieldSet)) {
            return validateUtils;
        }
        for (Field field : fieldSet) {
            Annotation[] annotations = ValidateCache.getInstance().getAnnotationsByField(field);
            if (null == annotations) {
                annotations = field.getAnnotations();
                ValidateCache.getInstance().setFieldAnnotations(field, annotations);
            }
            if (CommonUtil.isNull(annotations)) {
                return validateUtils;
            }
            Object fieldValue;
            try {
                fieldValue = PropertyUtils.getProperty(value, field.getName());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            for (Annotation annotation : annotations) {
                if (annotation instanceof NotNull) {
                    validateUtils.and(fieldValue).notNull(((NotNull) annotation).msg());
                } else if (annotation instanceof Max) {
                    Max max = (Max) annotation;
                    validateUtils.and(fieldValue).max(max.value(), max.msg());
                } else if (annotation instanceof Min) {
                    Min min = (Min) annotation;
                    validateUtils.and(fieldValue).min(min.value(), min.msg());
                } else if (annotation instanceof MaxLength) {
                    MaxLength maxLength = (MaxLength) annotation;
                    validateUtils.and(fieldValue).maxLength(maxLength.value(), maxLength.msg());
                } else if (annotation instanceof MinLength) {
                    MinLength minLength = (MinLength) annotation;
                    validateUtils.and(fieldValue).minLength(minLength.value(), minLength.msg());
                } else if (annotation instanceof Email) {
                    validateUtils.and(fieldValue).email(((Email) annotation).msg());
                } else if (annotation instanceof Phone) {
                    validateUtils.and(fieldValue).phone(((Phone) annotation).msg());
                } else if (annotation instanceof IdCard) {
                    validateUtils.and(fieldValue).idCard(((IdCard) annotation).msg());
                } else if (annotation instanceof Regex) {
                    Regex regex = (Regex) annotation;
                    validateUtils.and(fieldValue).regex(regex.value(), regex.msg());
                } else if (annotation instanceof Date) {
                    Date date = (Date) annotation;
                    String format = date.format();
                    validateUtils.and(fieldValue).date(format, date.msg());
                } else if (annotation instanceof Chinese) {
                    validateUtils.and(fieldValue).chinese(((Chinese) annotation).msg());
                } else if (annotation instanceof English) {
                    validateUtils.and(fieldValue).english(((English) annotation).msg());
                } else if (annotation instanceof IP) {
                    validateUtils.and(fieldValue).ip(((IP) annotation).msg());
                }
            }
        }
        return validateUtils;
    }

}
