package com.fangcloud.noah.api.api.service;

import java.util.Map;

import com.fangcloud.noah.api.api.model.RiskResult;
/**
 * Created by chenke on 16-9-8.
 */
public interface RiskInvokeService {

     RiskResult invoke(Map<String,Object> parameterMap);
}
