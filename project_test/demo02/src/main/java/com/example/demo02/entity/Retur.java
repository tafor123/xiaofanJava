package com.example.demo02.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Retur extends BaseEntity  {

    private String book_name;
    private String book_no;
    private String user_no;//会员卡
    private String user_name;
    private String user_phone;
    private String score; //会员积分
    private String status; //借书 状态
    private Integer days; //借书天数
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate createtime;//借书时间
    private LocalDate return_date;//归还时间
    private LocalDate real_date;//实际归还时间

}
