package com.fangcloud.noah.api.api.service;

import com.fangcloud.noah.api.api.enums.NameListBusinessType;

/**
 * 名单列表服务
 *
 * Created by chenke on 16-11-10.
 */
public interface NameListService {


    /**
     * 手机号是否是黑名单
     * @param mobile　手机号
     * @param type 业务类型
     * @return
     */
    public  boolean isBlackList(String mobile, NameListBusinessType type);

}
