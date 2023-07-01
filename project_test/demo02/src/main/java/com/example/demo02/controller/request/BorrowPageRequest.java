package com.example.demo02.controller.request;

import lombok.Data;

@Data
public class BorrowPageRequest extends BaseRequest {
    private String book_name;
    private String user_name;
    private String book_no;
}
