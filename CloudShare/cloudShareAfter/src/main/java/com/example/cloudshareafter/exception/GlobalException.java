package com.example.cloudshareafter.exception;

import com.example.cloudshareafter.commen.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 功能：
 * 作者：程序员子凡
 * 日期：2023/9/21 14:19
 */
@ControllerAdvice
public class GlobalException {


    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public Result serviceException(ServiceException e){
        return Result.error(e.getCode(),e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result globalException(Exception e) {
        e.printStackTrace();
        return Result.error("500", "系统错误");
    }
}
