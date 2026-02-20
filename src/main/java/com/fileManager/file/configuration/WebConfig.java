package com.fileManager.file.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // On mappe l'URL vers le dossier physique
        registry.addResourceHandler("/api/users/photos/**")
                .addResourceLocations("file:storage/");
    }
}