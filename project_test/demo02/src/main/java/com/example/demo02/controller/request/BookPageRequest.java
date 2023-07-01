package com.example.demo02.controller.request;

import lombok.Data;

@Data
public class BookPageRequest extends BaseRequest {
    private String name;
    private String book_no;
}
