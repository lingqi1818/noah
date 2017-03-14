package com.fangcloud.noah.common.api.util;

import java.util.ResourceBundle;

import com.fangcloud.noah.common.api.util.ApiCode.ApiCodeException;

/**
 *
 * @author chenke Dec 10, 2015
 */
public class ApiCodeUtil {

    private ResourceBundle bundle;

    public ApiCodeUtil(String name) {
        try {
            this.bundle = ResourceBundle.getBundle(name);
        } catch (Exception e) {
            throw new ApiCodeException("api code properties named [" + name + "] is not found.");
        }
    }

    public String getApiMessage(String apiCode, Object... params) throws ApiCodeException {
        if (apiCode == null) {
            throw new ApiCodeException("api code is null.");
        }
        String msg = bundle.getString(apiCode);
        if (msg == null) {
            throw new ApiCodeException("api code named [" + apiCode + "] is not found.");
        }
        if (params == null || params.length == 0) {
            return msg;
        }
        try {
            return String.format(msg, params);
        } catch (Exception e) {
            throw new ApiCodeException("api code named [" + apiCode + "]'s message format is invalid.");
        }
    }
}
