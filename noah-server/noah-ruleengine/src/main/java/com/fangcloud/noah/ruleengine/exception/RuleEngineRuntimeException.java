package com.fangcloud.noah.ruleengine.exception;

/**
 * Created by chenke on 16-8-19.
 */
public class RuleEngineRuntimeException extends RuntimeException {
    public RuleEngineRuntimeException() {
        super();
    }

    public RuleEngineRuntimeException(String message) {
        super(message);
    }

    public RuleEngineRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public RuleEngineRuntimeException(Throwable cause) {
        super(cause);
    }

    protected RuleEngineRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
