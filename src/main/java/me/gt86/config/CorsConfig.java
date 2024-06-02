package me.gt86.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
public class CorsConfig {

    @Value("#{'${allowed.origins}'.split(',')}")
    private String[] allowedOrigins;

    private static final Logger logger = LoggerFactory.getLogger(CorsConfig.class);

    @Bean
    public CorsFilter corsFilter() {
        logger.info("Configuring CORS with allowed origins: {}", Arrays.toString(allowedOrigins));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration apiConfig = new CorsConfiguration();

        apiConfig.setAllowCredentials(true);
        apiConfig.setAllowedOrigins(Arrays.asList(allowedOrigins));
        apiConfig.addAllowedHeader("*");
        apiConfig.addAllowedMethod(HttpMethod.POST);

        source.registerCorsConfiguration("/player/**", apiConfig);
        source.registerCorsConfiguration("/payment/**", apiConfig);

        CorsConfiguration globalConfig = new CorsConfiguration();
        globalConfig.setAllowCredentials(true);
        globalConfig.addAllowedHeader("*");
        globalConfig.addAllowedMethod(HttpMethod.POST);
        source.registerCorsConfiguration("/**", globalConfig);

        return new CorsFilter(source);
    }
}
