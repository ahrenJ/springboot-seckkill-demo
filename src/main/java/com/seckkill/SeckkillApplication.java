package com.seckkill;

import com.seckkill.dao.UserInfoMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan("com.seckkill.dao")
@EnableCaching
public class SeckkillApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeckkillApplication.class, args);
    }

}

