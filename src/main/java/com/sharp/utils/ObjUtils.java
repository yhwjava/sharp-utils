package com.sharp.utils;

import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>Title: FileUtil</p>
 * <p>Description: 常用工具集合，Object的判空，唯一id的生成，证件脱敏。。</p>
 * <p>Copyright: Copyright (c) 2019</p>
 *
 * @author yuanhongwei
 * @version 1.0 2019-6-30 下午6:48:33 【初版】
 */
public class ObjUtils {

    private static String serialVersionUID = "serialVersionUID";
    private static final String GET = "get";
    private static final String SET = "set";
    private static final String STRING = "java.lang.String";
    private static final String DATE = "java.util.Date";
    private static final String LONG = "java.lang.Long";

    /**
     * 对象非空判断 空为true
     * OoOo
     *
     * @param
     * @return
     */
    public static boolean isEmpty(Object obj) {
        return ObjectUtils.isEmpty(obj);
    }

    /**
     * 可定制 非空判断
     * 当传入对象只需一个不为空即满足则allowPartEmpty  为true
     * 如果验证所有入参都不能为空则allowPartEmpty  为false
     *
     * @param allowPartEmpty 是否允许 部分为空
     * @param obj            非空判断对象
     * @return
     */
    public static boolean isEmpty(boolean allowPartEmpty, Object... obj) {
        for (Object o : obj) {
            if (allowPartEmpty && !isEmpty(o)) {
                return false;
            } else if (!allowPartEmpty) {
                if (isEmpty(o)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 集合非空判断
     *
     * @param
     * @return
     * @author yuanhongwei
     */
    public static boolean collectionIsNull(Object collection) {
        boolean isNull = false;
        if (isEmpty(collection)) {
            isNull = true;
            return isNull;
        }
        if (collection instanceof Map) {
            Map map = (Map) collection;
            isNull = map.isEmpty();
        } else if (collection instanceof List) {
            List list = (List) collection;
            isNull = list.isEmpty();
        } else if (collection instanceof String[]) {
            String[] list = (String[]) collection;
            if (list != null || list.length > 0) {
                isNull = false;
            }
        } else if (collection instanceof Set) {
            Set set = (Set) collection;
            isNull = set.isEmpty();
        }
        return isNull;
    }


    /**
     * 判断该对象是否: 返回ture表示所有属性为null  返回false表示不是所有属性都是null(暂定)
     *
     * @param obj
     * @return boolean
     * @description
     * @author yuanhongwei
     */
    public static boolean isAllFieldNull(Object obj) throws Exception {
        Class stuCla = (Class) obj.getClass();
        //得到属性集合
        Field[] fs = stuCla.getDeclaredFields();
        boolean flag = true;
        for (Field f : fs) {
            if (!f.getName().equals(serialVersionUID)) {
                // 设置属性是可以访问的(私有的也可以)
                f.setAccessible(true);
                String name = f.getName();
                // 得到此属性的值
                Object val = f.get(obj);
                //只要有1个属性不为空,那么就不是所有的属性值都为空
                if (val != null) {
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }

    /**
     * 集合去重（如果集合对象是类，那必须重写equals和hashCode方法才有效）
     *
     * @param list 目标对象
     * @author yuanhongwei
     */
    public static void removeDuplicate(List list) {
        LinkedHashSet<String> set = new LinkedHashSet<String>(list.size());
        set.addAll(list);
        list.clear();
        list.addAll(set);
    }


    /**
     * 地址脱敏
     *
     * @param str 目标地址
     * @author yuanhongwei
     */
    public static String addressDesensitization(String str) {
        if (!isEmpty(str)) {
            str = str.trim();
            for (int i = 0; i < str.length(); i++) {
                if (Character.isDigit(str.charAt(i))) {
                    str = str.replace(String.valueOf(str.charAt(i)), "*");
                }
            }
        }
        return str;
    }


    /**
     * 证件脱敏
     *
     * @param certNumb 证件编码
     * @param isIDCert 是否为身份证
     * @author yuanhongwei
     */
    public static String certificatesDesensitization(Boolean isIDCert, String certNumb) {
        if (ObjUtils.isEmpty(certNumb)) {
            return certNumb;
        }
        if (isIDCert && certNumb.length() >= 15) {
            String var1 = certNumb.substring(0, 6);
            String var2 = certNumb.substring(certNumb.length() - 4, certNumb.length());
            certNumb = var1 + "********" + var2;
        } else if (!isIDCert && certNumb.length() >= 5) {
            String var1 = certNumb.substring(0, 5);
            String var2 = certNumb.substring(certNumb.length() - 1, certNumb.length());
            StringBuilder sb = new StringBuilder(certNumb);
            sb.replace(certNumb.length() - 4, certNumb.length() - 1, "***");
            certNumb = sb.toString();
        }
        return certNumb;
    }


    /**
     * 覆盖部分属性（适用于patch方式修改数据信息，先查询当前信息，之后再将需要更新的信息重新赋值到当前对象）
     *
     * @param clazz
     * @param nFiled
     */
    public static void setFiledValue(Object clazz, Map<String, Object> nFiled) {
        Method targetMtd = null;
        Class cz = clazz.getClass();
        Field[] fs = cz.getDeclaredFields();
        for (String key : nFiled.keySet()) {
            try {
                Field f = cz.getDeclaredField(key.trim());
                Type t = f.getGenericType();
                String mdName = toUpperCaseFirstOne(key);
                switch (t.getTypeName()) {
                    case STRING:
                        targetMtd = clazz.getClass().getMethod("set" + mdName, String.class);
                        targetMtd.invoke(clazz, nFiled.get(key));
                        break;
                    case LONG:
                        targetMtd = clazz.getClass().getMethod("set" + mdName, Long.class);
                        targetMtd.invoke(clazz, Long.valueOf(nFiled.get(key).toString()));
                        break;
                    case DATE:
                        targetMtd = clazz.getClass().getMethod("set" + mdName, Date.class);
                        targetMtd.invoke(clazz, nFiled.get(key));
                        break;
                }


            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    //首字母转小写
    public static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }

    //首字母转大写
    public static String toUpperCaseFirstOne(String s) {
        if (Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }

    /**
     * 获取请求流水号
     *
     * @return String
     */
    public static String generateRequestId() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = formatter.format(currentTime);
        return "Request" + dateString + getRandom();
    }

    /**
     * 获取随机数:线程编号+随机数共10位
     *
     * @return String
     */
    public static String getRandom() {
        String randStr = "";
        String threadId = String.valueOf(Thread.currentThread().getId());
        int len = threadId.length();
        if (len >= 10)
            threadId = threadId.substring(len - 10);
        else {
            for (int i = 0; i < 10 - len; i++) {
                randStr = randStr + (int) (Math.random() * 10.0D);
            }
        }
        return threadId + randStr;
    }

    public static String fillResMsg(String resMsg, String... params) {
        if (params == null || params.length == 0) {
            return resMsg;
        }
        for (String param : params) {
            resMsg = resMsg.replaceFirst("\\#\\?", param);
        }
        return resMsg;
    }

    /**
     * 操作类内部流水号，用于追溯业务调用流程
     *
     * @return
     */
    public static String generateSerialNum() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = formatter.format(currentTime);
        return "SYS" + dateString + getRandom();
    }

}


