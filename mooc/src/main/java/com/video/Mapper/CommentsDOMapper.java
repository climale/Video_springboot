package com.video.Mapper;

import com.video.Entity.Object.CommentsDO;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentsDOMapper {
    int deleteByPrimaryKey(String id);

    int insert(CommentsDO record);

    int insertSelective(CommentsDO record);

    CommentsDO selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CommentsDO record);

    int updateByPrimaryKeyWithBLOBs(CommentsDO record);

    int updateByPrimaryKey(CommentsDO record);
}