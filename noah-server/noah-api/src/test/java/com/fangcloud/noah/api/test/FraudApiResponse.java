//package com.fangcloud.noah.api.test;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import cn.fraudmetrix.riskservice.object.HitRule;
//import cn.fraudmetrix.riskservice.object.Policy;
//
//class FraudApiResponse implements Serializable {
//
//    private static final long   serialVersionUID = 844958112006659504L;
//    private boolean             success;                               // 执行是否成功，不成功时对应reason_code
//    private String              reason_code;                           // 错误码及原因描述，正常执行完扫描时为空
//    private Integer             final_score;                           // 风险分数
//    private String              final_decision;                        // 最终的风险决策结果
//    private List<HitRule>       hit_rules        = new ArrayList<>();  // 命中规则列表
//    private String              seq_id;                                // 请求序列号，每个请求进来都分配一个全局唯一的id
//    private Integer             spend_time;                            // 花费的时间，单位ms
//    private Map<String, Object> device_info      = new HashMap<>();    // 设备信息
//    private Map<String, Object> geoip_info       = new HashMap<>();    // 地理位置信息
//    private String              policy_set_name;                       // 策略集名称
//    private String              risk_type;                             // 命中的风险类型
//    private List<Policy>        policy_set       = new ArrayList<>();  // 策略集
//
//    public boolean isSuccess() {
//        return success;
//    }
//
//    public void setSuccess(boolean success) {
//        this.success = success;
//    }
//
//    public String getReason_code() {
//        return reason_code;
//    }
//
//    public void setReason_code(String reason_code) {
//        this.reason_code = reason_code;
//    }
//
//    public Integer getFinal_score() {
//        return final_score;
//    }
//
//    public void setFinal_score(Integer final_score) {
//        this.final_score = final_score;
//    }
//
//    public String getFinal_decision() {
//        return final_decision;
//    }
//
//    public void setFinal_decision(String final_decision) {
//        this.final_decision = final_decision;
//    }
//
//    public List<HitRule> getHit_rules() {
//        return hit_rules;
//    }
//
//    public void setHit_rules(List<HitRule> hit_rules) {
//        this.hit_rules = hit_rules;
//    }
//
//    public String getSeq_id() {
//        return seq_id;
//    }
//
//    public void setSeq_id(String seq_id) {
//        this.seq_id = seq_id;
//    }
//
//    public Integer getSpend_time() {
//        return spend_time;
//    }
//
//    public void setSpend_time(Integer spend_time) {
//        this.spend_time = spend_time;
//    }
//
//    public Map<String, Object> getDevice_info() {
//        return device_info;
//    }
//
//    public void setDevice_info(Map<String, Object> device_info) {
//        this.device_info = device_info;
//    }
//
//    public Map<String, Object> getGeoip_info() {
//        return geoip_info;
//    }
//
//    public void setGeoip_info(Map<String, Object> geoip_info) {
//        this.geoip_info = geoip_info;
//    }
//
//    public String getPolicy_set_name() {
//        return policy_set_name;
//    }
//
//    public void setPolicy_set_name(String policy_set_name) {
//        this.policy_set_name = policy_set_name;
//    }
//
//    public String getRisk_type() {
//        return risk_type;
//    }
//
//    public void setRisk_type(String risk_type) {
//        this.risk_type = risk_type;
//    }
//
//    public List<Policy> getPolicy_set() {
//        return policy_set;
//    }
//
//    public void setPolicy_set(List<Policy> policy_set) {
//        this.policy_set = policy_set;
//    }
//
//    @Override
//    public String toString() {
//        return "success:" + this.success + "\nfinal_decision:" + this.final_decision
//                + "\ngeoip_info:" + this.geoip_info + "\ndevice_info:" + this.device_info
//                + "\nhit_rules:" + this.getHit_rules();
//    }
//}
