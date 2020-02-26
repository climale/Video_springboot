package com.video.Service;

import com.video.Entity.Object.BGMDO;
import org.springframework.stereotype.Service;

import java.util.List;



public interface BgmDoService {

  public List<BGMDO> GetBgm();
  public BGMDO queryBgmById(String id);
}
