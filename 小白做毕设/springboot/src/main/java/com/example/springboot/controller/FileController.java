package com.example.springboot.controller;

import cn.hutool.core.io.FileUtil;
import com.example.springboot.commen.AuthAccess;
import com.example.springboot.commen.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * 功能：
 * 作者：程序员子凡
 * 日期：2023/9/8 8:57
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${ip:localhost}")
    String ip;

    @Value("${server.port}")
    String port;

    private static final String ROOT_PATH = System.getProperty("user.dir") +File.separator+"files";


    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename(); //文件的原始名称  aaa.png
        String mainName = FileUtil.mainName(originalFilename); // aaa
        String extName = FileUtil.extName(originalFilename); //  png
        if(FileUtil.exist(ROOT_PATH)){
            FileUtil.mkdir(ROOT_PATH);//父级目录不存在就创建
        }
        if(FileUtil.exist(ROOT_PATH+ File.separator + originalFilename)){
              originalFilename= System.currentTimeMillis() + "_" + mainName + "." + extName;

        }
        File sabeFile = new File(ROOT_PATH + File.separator + originalFilename);
        file.transferTo(sabeFile);
        String url="http://"+ ip +":"+ port + "/file/download/" + originalFilename;
        return Result.success(url); //返回文件的链接，这个链接就是文件的下载地址，这个下载地址就是我的后台提供出来的

    }
    @AuthAccess
    @GetMapping("/download/{fileName}")
    public void download(@PathVariable String fileName, HttpServletResponse response) throws IOException {
       // response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));  // 附件下载
       // response.addHeader("Content-Disposition", "inline;filename=" + URLEncoder.encode(fileName, "UTF-8"));  // 预览
        String filePath = ROOT_PATH + File.separator + fileName;
        if (!FileUtil.exist(filePath)){
            return;
        }
        byte[] bytes = FileUtil.readBytes(filePath);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes); //写出数组为字节数组，也是文件的字节流数组
        outputStream.flush();
        outputStream.close();

    }
}
