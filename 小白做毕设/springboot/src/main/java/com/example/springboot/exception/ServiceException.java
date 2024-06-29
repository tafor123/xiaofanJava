package com.example.springboot.exception;

import lombok.Getter;

/**
 * 功能：
 * 作者：程序员子凡
 * 日期：2023/9/5 16:57
 */
@Getter
public class ServiceException extends RuntimeException{

    private String code;
    public ServiceException(String msg){
        super(msg);
        this.code= "500";
    }

    public ServiceException(String code, String msg){
        super(msg);
        this.code=code;
    }

}
