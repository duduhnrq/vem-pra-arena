package com.vempraarena.plataforma.config;

import jakarta.servlet.MultipartConfigElement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class MultipartConfig {

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        String location = System.getProperty("user.dir") + "/temp_uploads";
        File tempDir = new File(location);
        if (!tempDir.exists()) {
            tempDir.mkdirs();
        }
        
        // long maxFileSize, long maxRequestSize, int fileSizeThreshold
        long maxFileSize = 100 * 1024 * 1024; // 100MB
        long maxRequestSize = 100 * 1024 * 1024; // 100MB
        int fileSizeThreshold = 0;
        
        return new MultipartConfigElement(location, maxFileSize, maxRequestSize, fileSizeThreshold);
    }
}
