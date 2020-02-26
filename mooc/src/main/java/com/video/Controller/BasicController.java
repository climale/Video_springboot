package com.video.Controller;

import com.video.utils.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasicController {
@Autowired
    public RedisOperator redisOperator;

    public static final String USER_REDIS_SESSION="user-redis-session";

    public static final String FFEMPEG_PATH = "E:/ffmpeg/bin/ffmpeg.exe";

    public static final String FILE_PATH="E:/imooc-video";

    public static final Integer PAGE_SIZE= 5;
}
