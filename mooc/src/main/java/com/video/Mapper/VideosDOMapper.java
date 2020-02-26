package com.video.Mapper;

import com.video.Entity.Object.VideosDO;
import com.video.Entity.VO.VideosVO;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideosDOMapper {
    int deleteByPrimaryKey(String id);

    int insert(VideosDO record);

    int insertSelective(VideosDO record);

    VideosDO selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(VideosDO record);

    int updateByPrimaryKey(VideosDO record);

    /**
     *  @Select("select *,${priceType} price " +
    "from goods " +
    "where classification_id=#{classificationId} and alive=true " +
    "order by ${sortVariable} ${sequence}")
    List<GoodsVO> selectByClassificationId(Integer classificationId, String priceType, String sortVariable, String sequence);
     */

@Select("select v.*,u.face_image as face_image,u.nickname as nickname from videos v " +
        "left join users u on u.id = v.user_id " +
        "where v.status = 1 " +
        "order by v.create_time desc")
    List<VideosVO> queryUserVideo();

}