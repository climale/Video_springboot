package com.video.Mapper;

import com.video.Entity.Object.Users_like_videosDO;
import org.springframework.stereotype.Repository;

@Repository
public interface Users_like_videosDOMapper {
    int deleteByPrimaryKey(String id);

    int insert(Users_like_videosDO record);

    int insertSelective(Users_like_videosDO record);

    Users_like_videosDO selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Users_like_videosDO record);

    int updateByPrimaryKey(Users_like_videosDO record);
}