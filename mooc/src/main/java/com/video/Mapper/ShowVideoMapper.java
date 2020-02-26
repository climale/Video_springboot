package com.video.Mapper;


import com.video.Entity.VO.VideosVO;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowVideoMapper {

    @Select("select v.*,u.face_image as face_image,u.nickname as nickname from videos v " +
            "left join users u on u.id = v.user_id " +
            "where v.status = 1 " +
            "order by v.create_time desc")
    List<VideosVO> queryUserVideo();
}
