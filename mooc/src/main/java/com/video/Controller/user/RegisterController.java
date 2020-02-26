package com.video.Controller.user;

import com.video.Controller.BasicController;
import com.video.Entity.Object.UsersDO;

import com.video.Entity.VO.UsersVO;
import com.video.Service.UsersDoService;
import com.video.utils.IMoocJSONResult;
import com.video.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@Api(value = "用户注册登陆的接口",tags = {"注册和登陆的controller"})
@RequestMapping(value = "/re/")
public class RegisterController extends BasicController {
   @Autowired
    UsersDoService usersDoService;


   @ApiOperation(value = "用户注册",notes = "用户注册的接口")
    @PostMapping("regist")
    public IMoocJSONResult regist(@RequestBody UsersDO usersDO) throws Exception {
        //1.判断用户名和密码必须不为空

        String username= usersDO.getUsername();
        String password = usersDO.getPassword();
        System.out.println(username);
        System.out.println(password);
        if (StringUtils.isBlank(password) || StringUtils.isBlank(username)) {

            return IMoocJSONResult.ok("用户名和密码不能为空");
        }

        //2.判断用户名是否存在
        boolean usernameIsExist= usersDoService.queryUsernameIsExist(usersDO.getUsername());

        //3.保存用户，注册信息
        if(!usernameIsExist){
            usersDO.setPassword(MD5Utils.getMD5Str(usersDO.getPassword()));
            usersDO.setFansCounts(0);
            usersDO.setFollowCounts(1);
            usersDoService.saveUser(usersDO);
        }else{
            return IMoocJSONResult.errorMsg("用户名已存在");
        }

//        String uniquetToken = UUID.randomUUID().toString();
//        redisOperator.set(USER_REDIS_SESSION+":"+usersDO.getId(),uniquetToken,1000*60*30);
//
//        UsersVO usersVO = new UsersVO();
//        BeanUtils.copyProperties(usersDO,usersVO);
//        usersVO.setUserToken(uniquetToken);

        UsersVO usersVO=SetUserRedisSessionToken(usersDO);
        System.out.println("regist:"+usersVO.getId());
        return IMoocJSONResult.ok(usersVO);
    }

    public UsersVO SetUserRedisSessionToken(UsersDO users)
    {
        String uniquetToken = UUID.randomUUID().toString();
        redisOperator.set(USER_REDIS_SESSION+":"+users.getId(),uniquetToken,1000*60*30);

        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(users,usersVO);
        usersVO.setUserToken(uniquetToken);
        return usersVO;
    }
    @ApiOperation(value="用户登录", notes="用户登录的接口")
    @PostMapping("Login")
    public IMoocJSONResult Login(@RequestBody UsersDO usersDO) throws Exception {
        String username= usersDO.getUsername();
        String password = usersDO.getPassword();
        //1.判断用户名和密码必须不为空
        System.out.println("login"+username);
        System.out.println("login"+password);
        if (StringUtils.isBlank(password) || StringUtils.isBlank(username)) {

            return IMoocJSONResult.ok("用户名和密码不能为空");
        }
        //2.账号密码正确
       UsersDO userIsExist= usersDoService.queryIsLoginSucess(username,MD5Utils.getMD5Str(password));
        if(userIsExist!=null){
            //System.out.println("userIsExist");
            System.out.println("login_usersDO:"+userIsExist.getId());
            UsersVO usersVO = SetUserRedisSessionToken(userIsExist);
           System.out.println("login:"+usersVO.getId());
            return IMoocJSONResult.ok(usersVO);
        }else
        {
            return IMoocJSONResult.errorMsg("账号密码不正确");
        }

    }
    @ApiOperation(value="用户注销", notes="用户注销的接口")
    @ApiImplicitParam(name="userId", value="用户id", required=true,
            dataType="String", paramType="query")
    @PostMapping("Loginout")
    public IMoocJSONResult Loginout(String userid){
       System.out.println("loginout:"+userid);
        redisOperator.del(USER_REDIS_SESSION+":"+userid);
        return IMoocJSONResult.ok();
    }
}
