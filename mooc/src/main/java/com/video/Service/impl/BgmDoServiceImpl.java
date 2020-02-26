package com.video.Service.impl;

import com.video.Entity.Object.BGMDO;
import com.video.Mapper.BGMDOMapper;
import com.video.Service.BgmDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "BgmDoService")
public class BgmDoServiceImpl implements BgmDoService {

    @Autowired
    BGMDOMapper bgmdoMapper;
    @Override
    public List<BGMDO> GetBgm() {

        return bgmdoMapper.GetBgmList();
    }

    @Override
    public BGMDO queryBgmById(String id) {
        return bgmdoMapper.selectByPrimaryKey(id);
    }
}
