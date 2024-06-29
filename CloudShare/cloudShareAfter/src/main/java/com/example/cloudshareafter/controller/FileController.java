package com.example.cloudshareafter.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.crypto.SecureUtil;
import com.example.cloudshareafter.commen.AuthAccess;
import com.example.cloudshareafter.commen.Result;
import com.example.cloudshareafter.entity.Files;
import com.example.cloudshareafter.entity.Page;
import com.example.cloudshareafter.entity.User;
import com.example.cloudshareafter.service.FilesService;
import com.example.cloudshareafter.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 功能：
 * 作者：程序员子凡
 * 日期：2023/9/23 9:46
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${ip:localhost}")
    String ip;

    @Value("${server.port}")
    String port;

    private static final String ROOT_PATH = System.getProperty("user.dir") + File.separator + "files";
    // D:\Java程序\xiaofanJava\CloudShare\files

   @Resource
    FilesService filesService;

    /**
     *上传单文件
     */
    @PostMapping("/upload")
    public Result upload(@RequestParam MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename(); //文件的原始名称  aaa.png
        String mainName = FileUtil.mainName(originalFilename); // aaa
        String type = FileUtil.extName(originalFilename); //  png
        long size =file.getSize();

        if(!FileUtil.exist(ROOT_PATH)){
            FileUtil.mkdir(ROOT_PATH);//父级目录不存在就创建
        }
        if(!FileUtil.exist(ROOT_PATH+ File.separator + originalFilename)){
            originalFilename= System.currentTimeMillis() + "_" + mainName + "." + type;
        }
        File saveFile = new File(ROOT_PATH + File.separator + originalFilename);
        String url;
        //把文件存储到磁盘里
        file.transferTo(saveFile);
        //获取文件的md5
        String md5 = SecureUtil.md5(saveFile);
        //从数据库查询文件有相同记录
        Files dbfiles = getFileMd5(md5);
        if(dbfiles != null){
            url = dbfiles.getUrl();
            //文件已存在，删除上传的重复文件
            saveFile.delete();
        }else {
            //如不存在则不删除
            url="http://"+ ip +":"+ port + "/file/download/" + originalFilename;
        }
        User currentUser = TokenUtils.getCurrentUser();
        //存储到数据库
        Files files = new Files();
        files.setName(originalFilename);
        files.setType(type);
        files.setSize(size);
        files.setUrl(url);
        files.setMd5(md5);
        assert currentUser != null;
        files.setUserId(currentUser.getId());
        filesService.insert(files);
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

    private Files getFileMd5(String md5){
        List<Files> filesList =filesService.selectByMd5(md5);
        return filesList.size() == 0 ? null : filesList.get(0);

    }

    //查询所有信息
    @GetMapping("/selectAll")
    public Result selectByFileAll(){
      List<Files> filesList = filesService.selectByFileAll();
      return Result.success(filesList);
    }

    //分页多条件查询
    @AuthAccess
    @GetMapping("/selectByFilePage")
    public Result selectByFilePage(@RequestParam Integer pageNum,
                                   @RequestParam Integer pageSize,
                                   @RequestParam String name){
        Page<Files> page = filesService.selectByPage(pageNum,pageSize,name);
        return Result.success(page);
    }
    //修改文件信息

    //删除文件信息

    //批量删除文件信息

    //新增目录信息
    @PostMapping("/newFolder")
    public Result addFile(@RequestBody Files files){
        User currentUser = TokenUtils.getCurrentUser();
        Integer userId1=1;
        try {
            if(currentUser != null && currentUser.getId() != null){
                Integer userId = currentUser.getId();
                userId1 = userId;
            }
            Files folder = filesService.newFolder(files.getFilePid(), userId1, files.getName());
            return Result.success( folder);
        } catch (IllegalArgumentException e) {
            return Result.error("文件夹创建失败：" + e.getMessage());
        }
    }
    //新增文件信息
    @PostMapping("/newFile")
    public Result addNewFile(@RequestBody Files files){
        try {
            filesService.insertFile(files);
        } catch (Exception e) {
            if(e instanceof DuplicateKeyException){
                return Result.error("插入数据失败");
            }else {
                return Result.error("系统错误");
            }
        }
        return Result.success();
    }
}
