package com.video.Mapper;

import com.video.Entity.Object.search_recordsDO;
import org.springframework.stereotype.Repository;

@Repository
public interface search_recordsDOMapper {
    int deleteByPrimaryKey(String id);

    int insert(search_recordsDO record);

    int insertSelective(search_recordsDO record);

    search_recordsDO selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(search_recordsDO record);

    int updateByPrimaryKey(search_recordsDO record);
}