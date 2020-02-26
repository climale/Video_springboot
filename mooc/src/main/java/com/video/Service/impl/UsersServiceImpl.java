package com.video.Service.impl;

import com.video.Common.org.n3r.idworker.Sid;
import com.video.Entity.Object.UsersDO;
import com.video.Mapper.UsersDOMapper;
import com.video.Service.UsersDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

//执行类
@Service(value = "UsersDoService")
public class UsersServiceImpl implements UsersDoService{
    @Autowired
   private UsersDOMapper usersDOMapper;
    @Autowired
private Sid sid;

//如果其他bean调用这个方法,在其他bean中声明事务,那就用事务.如果其他bean没有声明事务,那就不用事务.
@Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUsernameIsExist(String username) {

        UsersDO usersDO=new UsersDO();
        usersDO.setUsername(username);
        UsersDO res=usersDOMapper.selectByUsername(username);

        return res==null?false:true;
    }
    //如果有事务, 那么加入事务, 没有的话新建一个(默认情况下)
@Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveUser(UsersDO usersDO) {
    usersDO.setNickname(usersDO.getUsername());
       String userId= sid.nextShort();
        usersDO.setId(userId);
        usersDOMapper.insert(usersDO);
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public UsersDO queryIsLoginSucess(String username, String password) {
        System.out.println("UsersDoService"+username);
        System.out.println("UsersDoService"+password);
        UsersDO res_login=usersDOMapper.selectForLogin(username,password);
      return res_login;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public void updateFaceImage(UsersDO usersDO) {
       usersDOMapper.UpdateFaceImage(usersDO.getFaceImage(),usersDO.getId());
    }
}
