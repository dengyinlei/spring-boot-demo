package com.xkcoding.log.aop.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName : RequestLogInfo  //类名
 * @Description : 日志封装类   //描述
 * @Author : HTB  //作者
 * @Date: 2021-12-27 21:48  //时间
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestLogInfo {
    /**
     * 请求链接
     */
    private String requestUri;
    /**
     * 接口描述
     */
    private String apiDesc;
    /**
     * 请求类型
     */
    private String requestType;
    /**
     * HTTP请求方法
     */
    private String httpMethod;
    /**
     * Class路径方法
     */
    private String classMethod;
    /**
     * 请求IP
     */
    private String requestIp;
    /**
     * 请求参数
     */
    private String requestParams;
    /**
     * 花费时间 单位毫秒(ms)
     */
    private Long costTimeMillis;
    /**
     * 接口返回结果
     */
    private String result;
}
