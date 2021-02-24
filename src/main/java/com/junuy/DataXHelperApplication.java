package com.junuy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import tk.mybatis.spring.annotation.MapperScan;

/**
 * 启动
 *
 * @author junuy 2021/2/24
 */
@SpringBootApplication
@MapperScan("com.junuy.mapper")
public class DataXHelperApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataXHelperApplication.class, args);
    }
}
