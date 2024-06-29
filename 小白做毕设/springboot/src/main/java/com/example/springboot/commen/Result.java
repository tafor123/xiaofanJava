package com.example.springboot.commen;

/**
 * 功能：
 * 作者：程序员子凡
 * 日期：2023/9/1 8:48
 */


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Result {

    public static final String CODE_SUCCESS = "200";
    public static final String CODE_AUTH_ERROR = "401";
    public static final String CODE_SYS_ERROR = "500";
/*
* 请求返回码 200 401 404 500
* */
    private String code;
    /**
     * msg表示错误的详细信息
     */

    private String msg;
    /**
     * 数据从什么地方返回出去
     * 就是这个data
     * user Object类型就是user
     * list object类型就是list
     * Map object类型就是Map
     * object extend Yushengjun
     */

    private Object data;


    public static Result success() {
        return new Result(CODE_SUCCESS, "请求成功", null);
    }

    public static Result success(Object data) {
        return new Result(CODE_SUCCESS, "请求成功", data);
    }

    public static Result error(String msg) {
        return new Result(CODE_SYS_ERROR, msg, null);
    }

    public static Result error(String code, String msg) {
        return new Result(code, msg, null);
    }

    public static Result error() {
        return new Result(CODE_SYS_ERROR, "系统错误", null);
    }




}
