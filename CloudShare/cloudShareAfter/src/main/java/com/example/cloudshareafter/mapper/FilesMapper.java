package com.example.cloudshareafter.mapper;

import com.example.cloudshareafter.entity.Files;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FilesMapper {
    @Insert("insert into `file`(name,type,size,url,md5,file_Pid,create_time,user_id) " +
            "values (#{name},#{type},#{size},#{url},#{md5},#{filePid},#{createTime},#{userId})")
    void insert(Files files);

    @Select("select * from `file` where md5=#{md5}")
    List<Files> selectByMd5(String md5);

    @Select("select * from `file` where order by id desc ")
    List<Files> selectByFileAll();

    @Select("select * from `file` where  name like concat('%',#{name},'%')" +
            " order by id desc limit #{skipNum}, #{pageSize} ")
    List<Files> selectByPage(@Param("skipNum") Integer skipNum, @Param("pageSize") Integer pageSize,
                             @Param("name") String name);
    @Select("select count(id) from `file` where name like concat('%',#{name},'%') order by id desc ")
    Integer selectCountByPage(@Param("name") String name);


    @Select("select * from `file` where file_pid=#{filePid}")
    List<Files> selectByFilePid(Integer filePid);
    @Select("select * from `file` where user_id=#{userId}")
    List<Files> selectByFileUserId(Integer userId);
    @Select("select * from `file` where name=#{name}")
    List<Files> selectByFileName(String name);
    @Select("select * from `file` where folder_type=#{folderType}")
    List<Files> selectByFileFolderType(Boolean folderType);

    @Select(" SELECT * FROM `file`" +
            "        WHERE file_pid = #{filePid} AND name = #{name}")
    List<Files> selectByNameAndParent(@Param("filePid") Integer filePid, @Param("name") String name);
}
