package com.video.Mapper;

import com.video.Entity.Object.UsersDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersDOMapper {
    int deleteByPrimaryKey(String id);

    int insert(UsersDO record);

    int insertSelective(UsersDO record);

    UsersDO selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UsersDO record);

    int updateByPrimaryKey(UsersDO record);

    /**
     *
     * @param username
     * @return
     */
    @Select("select * from users where username=#{username}")
    UsersDO selectByUsername(String username);

    /**
     *
     * @param username
     * @param password
     * @return
     */
    @Select("SELECT * FROM users WHERE username= #{username} AND password= #{password}")
    UsersDO selectForLogin(@Param("username")String username,@Param("password") String password);

    /**
     *
     * @param face_image
     * @param id
     */
    @Update("update users set face_image=#{face_image} where id=#{id}")
    void UpdateFaceImage(@Param("face_image") String face_image,
                         @Param("id") String id);


}