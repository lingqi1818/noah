package com.fangcloud.noah.api;

import javax.servlet.http.HttpServletRequest;

import com.fangcloud.noah.api.model.BlackListType;
import com.fangcloud.noah.api.model.EventCollectDefinition;
import com.fangcloud.noah.api.model.RiskEvent;

import java.util.Map;

/**
 * 亿方云风控服务API，用于采集风控数据进行风险决策
 *
 * @author chenke
 * @date 2015年11月17日 下午4:01:28
 */
public interface RiskServiceClient {

    public static String CHANNEL_NAME = "noah_risk_channel_2";

    public static String REDIS_DEVICE_INFO_KEY_PREFIX = "risk_device_info";

    public static String APPLICATION_COLLECT_DEFINITION = "risk_application_collect";



    /**
     * 收集风控数据到亿方云风控系统
     *
     * @param riskEvent
     */
    public void collect(RiskEvent riskEvent);


    /**
     * 手工发送事件信息
     * @param request
     * @param eventType   事件类型
     * @param collectData 采集参数
     */
    public void collect(HttpServletRequest request, String eventType, Map<String, Object> collectData);


//    /**
//     * 发送数据到同盾决策系统
//     *
//     * @param data
//     */
//    public FraudApiResponse sendToFraud(Map<String, Object> data);


    public boolean getSwitchStatus();


    public String getDeviceId(String yOrderId);


    public Map<String,EventCollectDefinition> getEventCollectDefinitionMap();


    public boolean isBlackList(BlackListType blackListType, String value);
}
