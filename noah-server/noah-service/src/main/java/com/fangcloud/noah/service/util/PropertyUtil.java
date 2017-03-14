package com.fangcloud.noah.service.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by chenke on 16-8-26.
 */
public class PropertyUtil extends PropertyPlaceholderConfigurer {

    private static Map<String, String> propertiesMap;

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
        super.processProperties(beanFactoryToProcess, props);

        propertiesMap = new HashMap<String, String>();
        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            String value = props.getProperty(keyStr);
            propertiesMap.put(keyStr, value);
        }

    }

    public static String getProperty(String name, String defaultValue) {
        String value = propertiesMap.get(name);

        if (StringUtils.isBlank(value)) {
            return defaultValue;
        }
        return value;
    }
}
