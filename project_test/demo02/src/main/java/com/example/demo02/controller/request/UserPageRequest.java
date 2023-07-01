package com.example.demo02.controller.request;

import lombok.Data;

@Data
public class UserPageRequest extends BaseRequest {
  private String name;
  private String phone;
}
