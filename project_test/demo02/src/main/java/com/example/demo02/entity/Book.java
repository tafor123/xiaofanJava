package com.example.demo02.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
public class Book extends BaseEntity implements Serializable {
    private static final long serializableUID =1L;
    private String name;
    private String description;
    private String publish_date;
    private String author;
    private String publish;
    private String category;
    private String book_no;
    private String cover;
    private Integer score;
    private Integer nums;

    private List<String> categories;


}
