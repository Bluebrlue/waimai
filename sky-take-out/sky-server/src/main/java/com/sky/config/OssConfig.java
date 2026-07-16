package com.sky.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OssConfig {

    @Value("${sky.aliyun.oss.endpoint}")
    private String endpoint;

    @Bean
    public OSS ossClient() {
        String accessKeyId = System.getenv("OSS_ACCESS_KEY_ID");
        String accessKeySecret = System.getenv("OSS_ACCESS_KEY_SECRET");
        return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }
}
