package com.fangcloud.noah.common.api.model;

/**
 * <pre>
 * ApiException: 需要包含api code信息 和api message信息.
 * api code接入标准
 *      格式: XY ABC (均为一串数字)
 *      其中: XY 为模块类型 ABC为该模块下, 业务编码
 * 按照会员线, 举例:
 * 01001: 用户注册失败, 该手机号已经存在
 * 01002: 用户注册失败, 昵称为敏感词
 * 等
 * </pre>
 * 
 * @author chenke Dec 10, 2015
 */
public class ApiException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String apiCode;
    private String apiMessage;

    public ApiException(String apiCode, String apiMessage) {
        build(apiCode, apiMessage);
    }

    public ApiException() {
        super();
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    public ApiException build(String apiCode, String apiMessage) {
        this.apiCode = apiCode;
        this.apiMessage = apiMessage;
        return this;
    }

    public String getApiCode() {
        return apiCode;
    }

    public String getApiMessage() {
        return apiMessage;
    }

}
