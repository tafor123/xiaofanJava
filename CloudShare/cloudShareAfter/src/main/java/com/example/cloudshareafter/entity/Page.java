package com.example.cloudshareafter.entity;

import lombok.Data;

import java.util.List;

/**
 * 功能：
 * 作者：程序员子凡
 * 日期：2023/9/22 15:04
 */
@Data
public class Page<T> {
    private Integer total;
    private List<T> list;
}
