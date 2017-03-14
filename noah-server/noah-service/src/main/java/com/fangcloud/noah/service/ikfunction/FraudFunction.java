package com.fangcloud.noah.service.ikfunction;
//package com.helijia.risk.ikfunction;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.TypeReference;
//import com.helijia.risk.util.SpringContextUtil;
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.Map;
//
///**
// * Created by chenke on 16-8-26.
// */
//public class FraudFunction {
//
//    private static final Logger logger = LoggerFactory.getLogger(FraudFunction.class);
//
//
//    /**
//     * 调用同盾
//     *
//     * @param eventDataJson
//     * @return
//     */
//    public Object invokeFraud(String eventDataJson) {
//
//        if (StringUtils.isBlank(eventDataJson)) {
//            return null;
//        }
//
//        try {
//
//            Map<String, Object> eventDataMap = JSON.parseObject(eventDataJson, new TypeReference<Map<String, Object>>() {
//            });
//
//            FraudService fraudService = SpringContextUtil.getBean(FraudService.class);
//
//            return fraudService.invokeFraud(eventDataMap);
//
//        } catch (Exception e) {
//            logger.error("FraudFunction inokedFraud exception", e);
//
//            return null;
//        }
//    }
//}
