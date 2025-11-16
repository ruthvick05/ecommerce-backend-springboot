package com.scaler.productservice.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    // create a bean of restTemplate
    // restTemplate is used to make rest calls, http calls
    @Bean
    public RestTemplate createRestTemplate() {
        return new RestTemplate();
    }
}
