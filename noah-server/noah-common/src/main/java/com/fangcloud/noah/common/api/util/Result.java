package com.fangcloud.noah.common.api.util;

import java.util.UUID;

/**
 * <pre>
 *  API 返回对象
 *  常见用法
 *  成功:
 *      1. 带返回值:        return new Result<Model>(user);
 *      2. 不带返回值:       return new Result<Void>();
 *      3. 自定义requestId: return new Result<Model>(requstId, true).setData(user);
 *  失败:
 *      1. 无返回值:        return new Result<Void>(false).setApiCodeMessage(code, message);
 *      2. 自定义requestId: return new Result<Void>(requstId, false).setApiCodeMessage(code, message);
 * </pre>
 * 
 * @author chenke Dec 16, 2015
 */
public class Result<T> {

    private String requestId;   // 请求ID, UUID, 用于确定请求唯一标识
    private boolean success;    // 请求成功与否
    private T data;             // 请求返回对象数据
    private String apiCode;     // 请求响应码
    private String apiMessage;  // 请求响应信息

    public Result() {
        this(true);
    }

    public Result(T data) {
        this(true);
        this.data = data;
    }

    public Result(boolean success) {
        this(UUID.randomUUID().toString(), success);
    }

    public Result(String requestId, boolean success) {
        this.requestId = requestId;
        this.success = success;
    }

    public String getRequestId() {
        return requestId;
    }

    public Result<T> setRequestId(String requestId) {
        this.requestId = requestId;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public Result<T> setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public T getData() {
        return data;
    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }

    public String getApiCode() {
        return apiCode;
    }

    public Result<T> setApiCode(String apiCode) {
        this.apiCode = apiCode;
        return this;
    }

    public String getApiMessage() {
        return apiMessage;
    }

    public Result<T> setApiMessage(String apiMessage) {
        this.apiMessage = apiMessage;
        return this;
    }

    public Result<T> setApiCodeMessage(String apiCode, String apiMessage) {
        return setApiCode(apiCode).setApiMessage(apiMessage);
    }

}
