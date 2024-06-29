package com.example.cloudshareafter.entity;

import cn.hutool.core.date.DateTime;
import lombok.Data;

import java.sql.Date;

/**
 * 功能：
 * 作者：程序员子凡
 * 日期：2023/9/26 22:43
 */
@Data
public class Files {
    private Integer id;
    private Integer userId;
    private String md5;
    private String name;
    private String type;
    private Long size;
    private String url;
    private Boolean isDelete;
    private Boolean enable;
    private DateTime createTime; //create_time
    private Boolean folderType; //目录或文件
    private Integer filePid;

}
