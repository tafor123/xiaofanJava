package com.example.demo02;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.util.unit.DataSize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.MultipartConfigElement;

@RestController
@SpringBootApplication
public class Demo02Application {

    public static void main(String[] args) {
        SpringApplication.run(Demo02Application.class, args);
    }

   //健康检查的接口
    @GetMapping
    public String health(){
        return "SUCCESS";
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
    MultipartConfigFactory factory = new MultipartConfigFactory();
    //单个文件最大
        factory.setMaxFileSize(DataSize.parse("102400KB")); //KB,MB
// 设置总上传数据总大小
     factory.setMaxRequestSize(DataSize.parse("102400KB"));
     return factory.createMultipartConfig();

    }
}
