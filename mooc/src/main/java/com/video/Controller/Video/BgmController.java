package com.video.Controller.Video;


import com.video.Controller.BasicController;
import com.video.Entity.Object.BGMDO;
import com.video.Entity.Object.UsersDO;
import com.video.Entity.Object.VideosDO;
import com.video.Service.BgmDoService;
import com.video.Service.VideoService;
import com.video.utils.FFMPEGconfig;
import com.video.utils.IMoocJSONResult;
import com.video.utils.PageResult;
import io.swagger.annotations.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

@RestController
@Api(value = "调用BGM接口",tags = "调用BGMcontroller")
@RequestMapping("/bgm")
public class BgmController extends BasicController{
 @Autowired
    BgmDoService bgmDoService;

    @Autowired
    VideoService videoService;


    @PostMapping("/getbgmlist")
 @ApiOperation(value = "得到背景音乐列表",notes = "背景音乐列表")
 public IMoocJSONResult getbgmlist(){
     return IMoocJSONResult.ok(bgmDoService.GetBgm());
 }


    @ApiOperation(value = "用户上传视频",notes = "用户上传视频")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId", value="用户id", required=true,
                    dataType="String", paramType="query"),
            @ApiImplicitParam(name="bgmId", value="背景音乐id", required=true,
                    dataType="String", paramType="query"),
            @ApiImplicitParam(name="videoSecond", value="背景音乐的播放长度", required=true,
                    dataType="double", paramType="query"),
            @ApiImplicitParam(name="videoWidth", value="视频的宽度", required=true,
            dataType="int", paramType="query"),
            @ApiImplicitParam(name="videoHight", value="视频的高度", required=true,
                    dataType="int", paramType="query"),
            @ApiImplicitParam(name="desc", value="视频的描述", required=false,
                    dataType="String", paramType="query"),

    })

    @PostMapping(value = "uploadVideo",headers="content-type=multipart/form-data")
    public IMoocJSONResult uploadVideo(String userId, String bgmId, double videoSecond, int videoWidth, int videoHight, String desc, @ApiParam(value = "短视频",required = true) MultipartFile file) throws IOException {
        String fileVideoPath = "";
        String uploadVideoPathDB="";
        String CatchPicName = "";
        String fileCatchImagePath = "";
     if (StringUtils.isBlank(userId)){
            return IMoocJSONResult.errorMsg("用户名不能为空");

        }
        //文件保存的命名空间
        //String fileSpace="E:/imooc-video";
        //保存到数据库中的相对路径
        String uploadPathDB = "/"+userId+"/video/";
        String uploadCoverPathDB = "/"+userId + "/video/";
        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;

        try {
            if (file != null ) {
                String fileName = file.getOriginalFilename();
                if (StringUtils.isNotBlank(fileName)) {
                    //文件上传的最终保存的路径
                     fileVideoPath = FILE_PATH + uploadPathDB + fileName;
                    //设置数据库保存的路径
                    uploadPathDB += fileName;
                    //封面最终上传路径
                  //  fileCatchImagePath = FILE_PATH +uploadCoverPathDB + fileName;
                    File outFile = new File(fileVideoPath);
                    //File imageFile = new File(fileCatchImagePath);
                    if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
                        //创建夫文件夹
                        outFile.getParentFile().mkdirs();
                    }
//                    if (imageFile.getParentFile() != null || !imageFile.getParentFile().isDirectory()) {
//                        //创建夫文件夹
//                        imageFile.getParentFile().mkdirs();
//                    }

                    fileOutputStream = new FileOutputStream(outFile);
                  //  fileOutputStream = new FileOutputStream(imageFile);
                    inputStream = file.getInputStream();
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



    if (StringUtils.isNotBlank(bgmId)){
        BGMDO bgmdo = bgmDoService.queryBgmById(bgmId);
        String mp3Path =FILE_PATH+bgmdo.getPath();
        FFMPEGconfig tool = new FFMPEGconfig(FFEMPEG_PATH);
        String videoInputPath = fileVideoPath;
        String videoOutputName = UUID.randomUUID().toString();
       String temp_mp4 = "/" + userId + "/temp" + videoOutputName+".mp4";
        uploadVideoPathDB =  "/" + userId + "/" + videoOutputName;
       String finalvideo =  FILE_PATH+ uploadVideoPathDB+".mp4";
       fileVideoPath = FILE_PATH + temp_mp4;
        uploadCoverPathDB +=videoOutputName+".jpg";
        CatchPicName =FILE_PATH + uploadCoverPathDB;
        tool.convertor(videoInputPath,fileVideoPath);
        tool.voiceandaudio(fileVideoPath,mp3Path,finalvideo,videoSecond);
        tool.CatchImage(finalvideo,CatchPicName);
    }

        VideosDO videosDO = new VideosDO();
    videosDO.setAudioId(bgmId);
    videosDO.setUserId(userId);
    videosDO.setVideoHeight(videoHight);
    videosDO.setVideoWidth(videoWidth);
    videosDO.setVideoDesc(desc);
    videosDO.setVideoPath(uploadVideoPathDB);
    videosDO.setStatus(1);
    videosDO.setVideoSeconds((float)videoSecond);
    videosDO.setCreateTime(new Date());
    videosDO.setCoverPath(uploadCoverPathDB);
   String videoId = videoService.saveVideo(videosDO);

        return IMoocJSONResult.ok(videoId);

    }

    @ApiOperation(value = "展示所有视频",notes = "展示所有视频")

    @PostMapping(value = "showAllVideos")
    public IMoocJSONResult showAllVideos(Integer page) throws  Exception{
     if (page == null ){
         page =1;
     }


     PageResult result =  videoService.getAllVIdeo(page,PAGE_SIZE);
     System.out.print("result.getTotal():"+result);
     return IMoocJSONResult.ok(result);
    }

//
//    @ApiOperation(value = "视频封面",notes = "视频封面")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name="userId", value="用户id", required=true,
//                    dataType="String", paramType="form"),
//            @ApiImplicitParam(name="videoId", value="视频主键id", required=true,
//                    dataType="String", paramType="form")
//    })
//    @PostMapping(value = "updateCoverImage",headers="content-type=multipart/form-data")
//    public IMoocJSONResult updateCoverImage(String userId,String videoId,@ApiParam(value="视频封面", required=true)MultipartFile file) throws IOException {
//        if (StringUtils.isBlank(userId)||StringUtils.isBlank(videoId)){
//            return IMoocJSONResult.errorMsg("用户名不能为空");
//
//        }
//
//        //文件保存的命名空间
//        String fileSpace="E:/imooc-video";
//        //保存到数据库中的相对路径
//        String uploadPathDB = "/"+userId+"/cover/";
//
//        FileOutputStream fileOutputStream = null;
//        InputStream inputStream = null;
//
//        try {
//            if (file != null ) {
//                String fileName = file.getOriginalFilename();
//                if (StringUtils.isNotBlank(fileName)) {
//                    //文件上传的最终保存的路径
//                    String finalFacePath = fileSpace + uploadPathDB + fileName;
//                    //设置数据库保存的路径
//                    uploadPathDB += fileName;
//
//                    File outFile = new File(finalFacePath);
//                    if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
//                        //创建夫文件夹
//                        outFile.getParentFile().mkdirs();
//                    }
//
//                    fileOutputStream = new FileOutputStream(outFile);
//                    inputStream = file.getInputStream();
//                    IOUtils.copy(inputStream,fileOutputStream);
//
//                }else{
//                    return IMoocJSONResult.errorMsg("filename为空");             }
//            }
//        }catch (Exception e) {
//            e.printStackTrace();
//        }finally {
//            if (fileOutputStream != null){
//                fileOutputStream.flush();
//                fileOutputStream.close();
//            }
//        }
//
//      // VideosDO videosDO = new VideosDO();
//        //videosDO.setId(userId);
//       // videosDO.setCoverPath();
//        videoService.updateVideoCover(videoId,uploadPathDB);
//        return IMoocJSONResult.ok();
//
//    }
//
//

}
