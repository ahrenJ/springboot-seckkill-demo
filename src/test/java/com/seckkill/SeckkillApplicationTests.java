package com.seckkill;

import com.seckkill.dao.UserInfoMapper;
import com.seckkill.domain.UserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SeckkillApplicationTests {

    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void contextLoads() {
        UserInfo userInfo=userInfoMapper.selectByPrimaryKey(1);
        System.out.println(userInfo==null? "null":userInfo.toString());
    }

    @Test
    public void testRedis(){
        ValueOperations<String ,String > valueOperations=stringRedisTemplate.opsForValue();
        valueOperations.set("name","hzr");
    }

    @Test
    public void testRedis2(){
        ValueOperations<String ,String > valueOperations=stringRedisTemplate.opsForValue();
        System.out.println(valueOperations.get("name"));
    }

}

