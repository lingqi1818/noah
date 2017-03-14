package com.fangcloud.noah.ruleengine.bean;


import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认属性抽取器
 *
 * @author chenke
 * @date 2016年1月26日 上午11:10:06
 */
public class DefaultAttributeExtractor implements AttributeExtractor {

    private static Map<String, Method> attributesMethodMap = new ConcurrentHashMap<String, Method>();
    private static String ATTRIBUTE_SPLIT_SYMBOL = "_";

    public DefaultAttributeExtractor() {

    }

    @Override
    public Object extractAttribute(Object inst, String expectRootName, String attrKey) {
        if (StringUtils.isEmpty(attrKey)) {
            throw new IllegalArgumentException("attrKey is empty !");
        }
        String clzzName = inst.getClass().getName();
        Class<?> clzz = inst.getClass();
        Class<?>[] clzzArray = new Class<?>[]{};
        Object[] objArray = new Object[]{};
        Method m = attributesMethodMap.get(clzzName + ATTRIBUTE_SPLIT_SYMBOL + attrKey);
        if (m != null) {
            try {
                m.invoke(inst, objArray);
            } catch (Exception e) {
                throw new RuntimeException(
                        "getMethod invoke error:" + m.getName() + ",class is:" + clzzName, e);
            }
        }
        String[] attrs = attrKey.split(ATTRIBUTE_SPLIT_SYMBOL);
        if (attrs != null) {
            int i = 0;
            for (String attr : attrs) {
                if (i++ == 0) {
                    if (!attr.equals(expectRootName)) {
                        throw new RuntimeException("expectRootName is error,true value is:" + attr);
                    }
                    continue;
                }
                attr = attr.substring(0, 1).toUpperCase() + attr.substring(1);
                try {
                    m = clzz.getDeclaredMethod("get" + attr, clzzArray);
                    m.setAccessible(true);
                    Object obj = m.invoke(inst, objArray);
                    clzz = obj.getClass();
                    inst = obj;
                } catch (Exception e) {
                    throw new RuntimeException(
                            "getMethod invoke error:get" + attr + ",class is:" + clzz.getName(), e);
                }
            }

            return inst;
        }
        return null;
    }

}
