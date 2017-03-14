package com.fangcloud.noah.dao.enums;

/**
 * Created by chenke on 16-8-20.
 */
public enum EventType {

    //注册
    register("100", "register", "0", DeviceType.UNKNOW),
    register_web("101", "register_web", "100", DeviceType.WEB),
    register_android("102", "register_android", "100", DeviceType.ANDROID),
    register_ios("103", "register_ios", "100", DeviceType.IOS),
    //登陆
    login("200", "login", "0", DeviceType.UNKNOW),
    login_web("201", "login_web", "200", DeviceType.WEB),
    login_android("202", "login_android", "200", DeviceType.ANDROID),
    login_ios("203", "login_ios", "200", DeviceType.IOS),
    //支付
    payment("300", "payment", "0", DeviceType.UNKNOW),
    payment_web("301", "payment_web", "300", DeviceType.WEB),
    payment_android("302", "payment_android", "300", DeviceType.ANDROID),
    payment_ios("303", "payment_ios", "300", DeviceType.IOS),
    //充值
    recharge("400", "recharge", "0", DeviceType.UNKNOW),
    recharge_web("401", "recharge_web", "400", DeviceType.WEB),
    recharge_android("402", "recharge_android", "400", DeviceType.ANDROID),
    recharge_ios("403", "recharge_ios", "400", DeviceType.IOS),

    //营销
    marketing("500", "marketing", "0", DeviceType.UNKNOW),
    marketing_web("501", "marketing_web", "500", DeviceType.WEB),
    marketing_android("502", "marketing_android", "500", DeviceType.ANDROID),
    marketing_ios("503", "marketing_ios", "500", DeviceType.IOS),

    //submitOrder
    submitOrder("600", "submitOrder", "0", DeviceType.UNKNOW),
    submitOrder_web("601", "search_web", "600", DeviceType.WEB),
    submitOrder_android("602", "search_android", "600", DeviceType.ANDROID),
    submitOrder_ios("603", "search_ios", "600", DeviceType.IOS),


    //支付回调
    pay_notify("700", "pay_notify", "0", DeviceType.UNKNOW),


    //闺蜜分享
    lottery("800", "lottery", "0", DeviceType.UNKNOW),
    lottery_web("801", "lottery_web", "800", DeviceType.WEB),
    lottery_android("802", "lottery_android", "800", DeviceType.ANDROID),
    lottery_ios("803", "lottery_ios", "800", DeviceType.IOS),

    //短信
    message("900", "message", "0", DeviceType.UNKNOW),
    message_web("901", "message_web", "900", DeviceType.WEB),
    message_android("902", "message_android", "900", DeviceType.ANDROID),
    message_ios("903", "message_ios", "900", DeviceType.IOS),

    //获取配置信息
    GETCONFIG("1000", "getconfig", "0", DeviceType.UNKNOW),


    //活动红包
    active_hongbao("1010", "active_hongbao", "0", DeviceType.UNKNOW),
    active_hongbao_web("1011", "active_hongbao_web", "1010", DeviceType.WEB),
    active_hongbao_android("1012", "active_hongbao_android", "1010", DeviceType.ANDROID),
    active_hongbao_ios("1013", "active_hongbao_ios", "1010", DeviceType.IOS);


    private String code;
    private String name;
    private String parent;
    private DeviceType deviceType;

    EventType(String code, String name, String parent, DeviceType deviceType) {
        this.code = code;
        this.name = name;
        this.parent = parent;
        this.deviceType = deviceType;
    }


    public static EventType codeOf(String code) {
        EventType[] riskTypes = EventType.values();
        for (EventType riskType : riskTypes) {
            if (riskType.getCode().equals(code)) {
                return riskType;
            }
        }
        return null;
    }

    public static EventType getRiskTypeByParentAndDeviceType(String parent, DeviceType deviceType) {
        EventType[] riskTypes = EventType.values();
        for (EventType riskType : riskTypes) {

            if (riskType.getParent().equals(parent) && riskType.getDeviceType().equals(deviceType)) {
                return riskType;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }
}
