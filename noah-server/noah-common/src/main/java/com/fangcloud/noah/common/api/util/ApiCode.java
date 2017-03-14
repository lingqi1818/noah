package com.fangcloud.noah.common.api.util;

/**
 *
 * @author chenke Dec 10, 2015
 */
public interface ApiCode {

    String getApiCode();
    
    String getApiMessage(Object... params) throws ApiCodeException;

    public static class ApiCodeException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public ApiCodeException(String message) {
            super(message);
        }

    }

}
