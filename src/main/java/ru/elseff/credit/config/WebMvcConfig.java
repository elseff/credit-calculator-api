package ru.elseff.credit.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String origin = "http://localhost:4200";
        registry.addMapping("/**")
                .allowedOrigins("http://192.168.100.4:4200")
                .allowedOrigins(origin)
                .allowedMethods("*");
        log.info("Allowed origin --- " + origin);
    }
}
