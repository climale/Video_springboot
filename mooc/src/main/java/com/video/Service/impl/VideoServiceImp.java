package com.video.Service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.video.Common.org.n3r.idworker.Sid;
import com.video.Entity.Object.VideosDO;
import com.video.Entity.VO.VideosVO;
import com.video.Mapper.ShowVideoMapper;
import com.video.Mapper.VideosDOMapper;
import com.video.Service.VideoService;
import com.video.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("VideoService")
public class VideoServiceImp implements VideoService {

    @Autowired
    VideosDOMapper videosDOMapper;

    @Autowired
    ShowVideoMapper showVideoMapper;
    @Autowired
    Sid sid;
    @Override
    public String saveVideo(VideosDO videosDO) {

        String id = sid.nextShort();
        videosDO.setId(id);
        videosDOMapper.insertSelective(videosDO);
        return id;
    }

    @Override
    public void updateVideoCover(String videoId, String Path) {
        VideosDO videosDO = new VideosDO();
        videosDO.setId(videoId);
        videosDO.setCoverPath(Path);
        videosDOMapper.updateByPrimaryKeySelective(videosDO);
    }

    @Override
    public List<VideosVO> getVideoList() {
        return null;
    }

    @Override
    public PageResult getAllVIdeo(Integer page, Integer pagesize) {
        System.out.println("page:"+page);
        System.out.println("pagesize:"+pagesize);
      PageHelper.startPage(page,pagesize);

        List<VideosVO> list = showVideoMapper.queryUserVideo();
        PageInfo<VideosVO> pageInfo = new PageInfo<VideosVO>(list);
        System.out.println("当前页码："+pageInfo.getPageNum());
        System.out.println("总记录数："+pageInfo.getTotal());
        System.out.println("每页的记录数："+pageInfo.getPageSize());
        System.out.println("总页码："+pageInfo.getPages());
        System.out.println("是否第一页："+pageInfo.isIsFirstPage());
        System.out.println("连续显示的页码：");
        PageResult pageResult = new PageResult();
        pageResult.setPage(page);
        pageResult.setTotal(pageInfo.getPages());
        pageResult.setRows(list);
        pageResult.setRecords(pageInfo.getTotal());


        return pageResult;
    }
}
