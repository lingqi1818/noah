package com.fangcloud.noah.ruleengine.bean;

/**
 * 属性抽取器
 *
 * @author chenke
 * @date 2016年1月25日 下午6:13:55
 */
public interface AttributeExtractor {
    public Object extractAttribute(Object inst, String expectRootName, String attrKey);
}
