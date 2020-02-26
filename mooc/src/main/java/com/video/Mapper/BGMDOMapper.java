package com.video.Mapper;

import com.video.Entity.Object.BGMDO;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BGMDOMapper {
    int deleteByPrimaryKey(String id);

    int insert(BGMDO record);

    int insertSelective(BGMDO record);

    BGMDO selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BGMDO record);

    int updateByPrimaryKey(BGMDO record);


    /**
     *
     * @return
     */
    @Select("select * from bgm")
    List<BGMDO> GetBgmList();
}