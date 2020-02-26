package com.video.Service;
import com.video.Entity.Object.UsersDO;



public interface UsersDoService {

    //判断用户名是否存在
     boolean queryUsernameIsExist(String username);

     void saveUser(UsersDO usersDO);

     //判断是否登陆成功
    UsersDO queryIsLoginSucess(String username,String passward);

    void updateFaceImage(UsersDO usersDO);
}
