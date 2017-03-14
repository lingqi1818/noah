package com.fangcloud.noah.api.exception;

/**
 * @author: YJL  Date: 15-11-23
 */
public class RiskRuntimeException extends RuntimeException {

    public RiskRuntimeException(String message) {
        super(message);
    }

    public RiskRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public RiskRuntimeException(Throwable cause) {
        super(cause);
    }

    protected RiskRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
