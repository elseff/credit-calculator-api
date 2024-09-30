package ru.elseff.credit.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Slf4j
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("#{'${allowed.origins}'.split(' ')}")
    List<String> origins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
//        origins.forEach(
//                origin -> {
//                    registry.addMapping("/**")
//                            .allowedOrigins(origin)
//                            .allowedMethods("*");
//                    log.info("Allowed origin --- " + origin);
//                }
//        );
        registry.addMapping("/**").allowedMethods("*");
    }
}
