package com.video.Service;

import com.video.Entity.Object.VideosDO;
import com.video.Entity.VO.VideosVO;
import com.video.utils.PageResult;

import java.util.List;

public interface VideoService {


    public String saveVideo(VideosDO videosDO);

    public void updateVideoCover(String videoId,String Path);

    public List<VideosVO> getVideoList();

    public PageResult getAllVIdeo(Integer page,Integer pagesize);
}
