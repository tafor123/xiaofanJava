package com.example.cloudshareafter.service;

import com.example.cloudshareafter.entity.Files;
import com.example.cloudshareafter.entity.Page;
import com.example.cloudshareafter.entity.User;
import com.example.cloudshareafter.exception.ServiceException;
import com.example.cloudshareafter.mapper.FilesMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 功能：
 * 作者：程序员子凡
 * 日期：2023/9/26 22:54
 */
@Service
public class FilesService {

    @Resource
    FilesMapper filesMapper;
    public void insert(Files files) {
        filesMapper.insert(files);
    }


    public List<Files> selectByMd5(String md5) {
        return filesMapper.selectByMd5(md5);
    }

    public List<Files> selectByFileAll() {
        return filesMapper.selectByFileAll();
    }

    public Page<Files> selectByPage(Integer pageNum, Integer pageSize, String name) {
        Integer skipNum =(pageNum-1) * pageSize; // 计算出来  1 -> 0,5    2 -> 5,5   3 -> 10,5
        Page<Files> page = new Page<>();
        List<Files> files = filesMapper.selectByPage(skipNum,pageSize,name);
        Integer total = filesMapper.selectCountByPage(name);
        page.setTotal(total);
        page.setList(files);
        return page;
    }


    public Files newFolder(Integer filePid, Integer userId1, String name) {
        // 假设folderType是true表示文件夹，false表示文件
        boolean folderType = true;

        // 校验文件名，仅在folderType为true（文件夹）时执行校验
        if (folderType) {
            checkFilename(filePid, userId1, name);
        }

        // 创建文件夹的逻辑
        Files folder = new Files();
        folder.setUserId(userId1);
        folder.setName(name);
        folder.setFolderType(folderType);
        folder.setFilePid(filePid);

        // 保存文件夹到数据库
        filesMapper.insert(folder);

        return folder;
    }

    // 校验文件名，检查在同一个父文件夹下是否存在相同名称的文件或文件夹
    private void checkFilename(Integer filePid, Integer userId1, String name) {
        List<Files> filesWithSameName = filesMapper.selectByNameAndParent(filePid, name);
        if (!filesWithSameName.isEmpty()) {
            throw new ServiceException("文件夹中已存在相同名称的文件或文件夹");
        }
    }

    public void insertFile(Files files) {
        filesMapper.insert(files);
    }
}
