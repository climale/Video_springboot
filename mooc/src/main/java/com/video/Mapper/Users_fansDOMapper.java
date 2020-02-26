package com.video.Mapper;

import com.video.Entity.Object.Users_fansDO;
import org.springframework.stereotype.Repository;

@Repository
public interface Users_fansDOMapper {
    int deleteByPrimaryKey(String id);

    int insert(Users_fansDO record);

    int insertSelective(Users_fansDO record);

    Users_fansDO selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Users_fansDO record);

    int updateByPrimaryKey(Users_fansDO record);
}