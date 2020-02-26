package com.video.Controller.user;

import com.video.Entity.Object.UsersDO;
import com.video.Service.UsersDoService;
import com.video.utils.IMoocJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import org.apache.commons.io.*;
import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@Api(value = "用户接口",tags = "用户接口controller")
@RequestMapping("/user")
public class UserController {
 @Autowired
    UsersDoService usersDoService;

 @ApiOperation(value = "用户上传头像",notes = "用户上传头像")
 @ApiImplicitParam(name="userId", value="用户id", required=true,
         dataType="String", paramType="query")
 @PostMapping("updateFaceImage")
    public IMoocJSONResult updateFaceImage(String userId, @RequestParam("file") MultipartFile[] files) throws IOException {
     if (StringUtils.isBlank(userId)){
         return IMoocJSONResult.errorMsg("用户名不能为空");

     }
     //文件保存的命名空间
     String fileSpace="E:/imooc-video";
     //保存到数据库中的相对路径
     String uploadPathDB = "/"+userId+"/face/";

     FileOutputStream fileOutputStream = null;
     InputStream inputStream = null;

     try {
         if (files != null && files.length > 0) {
             String fileName = files[0].getOriginalFilename();
             if (StringUtils.isNotBlank(fileName)) {
                 //文件上传的最终保存的路径
                 String finalFacePath = fileSpace + uploadPathDB + fileName;
                 //设置数据库保存的路径
                 uploadPathDB += fileName;

                 File outFile = new File(finalFacePath);
                 if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
                     //创建夫文件夹
                     outFile.getParentFile().mkdirs();
                 }

                 fileOutputStream = new FileOutputStream(outFile);
                 inputStream = files[0].getInputStream();
                 IOUtils.copy(inputStream,fileOutputStream);

             }else{
                 return IMoocJSONResult.errorMsg("filename为空");             }
         }
     }catch (Exception e) {
         e.printStackTrace();
     }finally {
         if (fileOutputStream != null){
             fileOutputStream.flush();
             fileOutputStream.close();
         }
     }

     UsersDO usersDO = new UsersDO();
     usersDO.setId(userId);
     usersDO.setFaceImage(uploadPathDB);
     usersDoService.updateFaceImage(usersDO);
     return IMoocJSONResult.ok(uploadPathDB);

 }


    @ApiOperation(value = "读取用户信息",notes = "读取用户信息")
    @ApiImplicitParam(name="userId", value="用户id", required=true,
            dataType="String", paramType="query")
    @PostMapping("getUserInfo")
    public IMoocJSONResult getUserInfo(@RequestBody UsersDO usersDO){
     UsersDO users= usersDoService.queryIsLoginSucess(usersDO.getUsername(),usersDO.getPassword());
     return IMoocJSONResult.ok(users);
    }

}
