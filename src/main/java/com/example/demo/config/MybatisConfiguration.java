package com.example.demo.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Genius
 * @date 2023/07/28 16:15
 **/

@Configuration
@MapperScan("com.example.demo.mapper")
public class MybatisConfiguration {
}
