package com.example.demo.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Files;
import com.example.demo.entity.User;
import com.example.demo.mapper.FileMapper;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 文件上传相关
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${files.upload.path}")
    private String fileUploadPath;

    @Resource
    private FileMapper fileMapper;

    /**
     * 文件上传
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        long size = file.getSize();

        //存储到磁盘

        //定义一个文件唯一表示
        String uuid = IdUtil.fastSimpleUUID();
        String fileUuid = uuid + StrUtil.DOT + type;
        File uploadFile = new File(fileUploadPath + fileUuid );

        //判段配置文件目录是否存在，若不存在则创建一个新的文件目录
        File parentFile = uploadFile.getParentFile();
        if (!parentFile.exists()){
            parentFile.mkdirs();
        }


        String url;
        //上传的文件存储到磁盘目录
        file.transferTo(uploadFile);
        String md5 = SecureUtil.md5(uploadFile);
        //从数据库查询是否有相同的md5
        Files dbFiles = getFilesMd5(md5);
        if (dbFiles != null){
            url = dbFiles.getUrl();
            //文件存在，所以删除上传的重复文件
            uploadFile.delete();
        }else{
            url = "http://localhost:9090/file/" + fileUuid;
        }



        //存储到数据库
        Files saveFile = new Files();
        saveFile.setName(originalFilename);
        saveFile.setType(type);
        saveFile.setSize(size/1024);
        saveFile.setUrl(url);
        saveFile.setMd5(md5);
        fileMapper.insert(saveFile);

        return url;
    }


    /**
     * 文件下载接口  http://localhost:9090/file/{fileUuid}
     * @param fileUuid
     * @param response
     * @throws IOException
     */
    @GetMapping("/{fileUuid}")
    public void download(@PathVariable String fileUuid,HttpServletResponse response) throws IOException {
        //根据文件唯一标识获取文件
        File uploadFile = new File(fileUploadPath + fileUuid);
        //设置文件输出格式
        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileUuid,"UTF-8"));
        response.setContentType("application/octet-stream");

        //读取文件字节流
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        os.close();
    }


    /**
     * 通过文件的md5查询文件
     * @param md5
     * @return
     */
    private Files getFilesMd5(String md5){
        //查询文件md5
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("md5",md5);
        List<Files> filesList = fileMapper.selectList(queryWrapper);
        return filesList.size() == 0 ? null : filesList.get(0);
    }


    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        Files files = fileMapper.selectById(id);
        files.setIsDelete(true);
        return Result.success(fileMapper.updateById(files));
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @PostMapping("/del/betch")
    public Result deleteBetch(@RequestBody List<Integer> ids){
        //select * from sys_file where id in (id,id,id...)
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id",ids);
        List<Files> files = fileMapper.selectList(queryWrapper);
        for (Files file : files){
            file.setIsDelete(true);
            fileMapper.updateById(file);
        }

        return Result.success();
    }


    /**
     * 新增或更新
     * @param files
     * @return
     */
    @PostMapping("/update")
    public Result save(@RequestBody Files files){

        return Result.success(fileMapper.updateById(files));
    }


    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name){
        IPage<Files> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        //查询为删除的记录
        queryWrapper.eq("is_delete", false);
        if (!"".equals(name)){
            queryWrapper.like("name",name);
        }

        return Result.success(fileMapper.selectPage(new Page<>(pageNum, pageSize), queryWrapper));
    }

}
