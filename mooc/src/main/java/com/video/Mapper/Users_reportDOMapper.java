package com.video.Mapper;

import com.video.Entity.Object.Users_reportDO;
import org.springframework.stereotype.Repository;

@Repository
public interface Users_reportDOMapper {
    int deleteByPrimaryKey(String id);

    int insert(Users_reportDO record);

    int insertSelective(Users_reportDO record);

    Users_reportDO selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Users_reportDO record);

    int updateByPrimaryKey(Users_reportDO record);
}