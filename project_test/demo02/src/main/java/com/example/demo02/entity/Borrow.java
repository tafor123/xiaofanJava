package com.example.demo02.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Borrow extends BaseEntity  {

    private String book_name;
    private String book_no;
    private String user_no;//会员卡
    private String user_name;
    private String user_phone;
    private Integer score; //会员积分
    private String status; //借书 状态
    private Integer days; //借书天数
    //@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate return_date;//归还时间
    private String note; //提醒状态，即将到期
}
